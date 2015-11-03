package com.bankapp.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.CertificatePolicies;
//import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.dao.UserDAO;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


@SuppressWarnings({ "deprecation", "unused" })
public class CertServiceImpl implements CertService {
	
	@Autowired 
	EmailService email;
	@Autowired
	UserDAO userDAO;
	
	//create ROOT's Certificate and pfx instances
    private java.security.cert.X509Certificate root_cert;
    private KeyStore root_ks;
    //private String sub_path="/com.bankapp.resources";
    private String sub_path="";
    private String path; 
    //create cert and pfx of a given username   
    private String user_cert_path;
    private String user_pfx_path;
    
    
    //set username and password of pfx of bank
	private String root_pwd="admin";
	private String root_name="admin";
	
	private String fromEmail = "SunDevilBankASU@gmail.com";
    
    public void loadRoot() throws Exception{
    	//add provider used in keyPairGenerateor
		Security.addProvider(new BouncyCastleProvider());
		//get path of folder
		String parent_path=new File("").getAbsolutePath();
		path = parent_path+sub_path;
		
		
	    
	    //create following file to check if root's certificate and phx exit
	    File root_cer_file = new File(path+"/rootCert.cer");
	    File root_pfx_file = new File(path+"/rootPfx.pfx");
	    
	    
	    
		
	    KeyPair root_pair = null;
	    
	   //if both files exit, we load both of them
	    if(root_cer_file.exists()&&root_pfx_file.exists()){
	    	//used to generate or load files
		    FileInputStream f_input_cer = new FileInputStream(path+File.separatorChar+"rootCert.cer");
	    	FileInputStream f_input_pfx = new FileInputStream(path+File.separatorChar+"rootPfx.pfx");
	    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
	    	
	    	//load certificate
	    	root_cert=(X509Certificate)cf.generateCertificate(f_input_cer);
	    	
	    	//load pfx
	    	root_ks = KeyStore.getInstance("PKCS12");  
			  
            root_ks.load(f_input_pfx,root_pwd.toCharArray());  //use this password to load the input file
            f_input_pfx.close(); 
	    }
	    
	    //if only pfx file exists, we can generate a certificate using it
	    else if(!root_cer_file.exists()&&root_pfx_file.exists()){
	    	FileInputStream f_input_pfx = new FileInputStream(path+File.separatorChar+"rootPfx.pfx");

	    	//load pfx
	    	root_ks = KeyStore.getInstance("PKCS12"); 
            root_ks.load(f_input_pfx,root_pwd.toCharArray());  //use this password to load the input file
            f_input_pfx.close(); 
            
            //create certificate
            root_cert=(X509Certificate) root_ks.getCertificate(root_name);
            
	    }
	    
	    //if both don't exist, or only certificate doesn't exist, we need create both
	    else{
	    	//create certificate
	    	root_pair = this.generateRSAKeyPair();
		    root_cert = this.generateV3Certificate(root_pair,root_name);
		    
		    //save certificate
		    this.saveCert(root_cert, path, "rootCert.cer");
		    //create keystore
		    root_ks = this.genKS(root_pair, root_pwd.toCharArray(), root_cert, root_name, root_cert);
		    
		    //save keystore as pfx
		    this.savePfx(root_ks, path, root_pwd, "rootPfx.pfx");
	    }
    			
    }
    		
    public void initCertPfx(String username, String password) throws Exception{
    	
    	Security.addProvider(new BouncyCastleProvider());
	    //generate keypair of client
	    KeyPair pair = this.generateRSAKeyPair();
	    
	    
	    
	    PublicKey pubKey = pair.getPublic(); 
	    String publicKey = Base64.getEncoder().encodeToString((pubKey.getEncoded()));

	    
	    userDAO.insertPubKey(username, publicKey);
	    
	    //generate certificate of client
	    java.security.cert.X509Certificate cert = this.generateV3Certificate(pair,username);
	    
	    //save  certificate of client
	    this.saveCert(cert,path,username+"userCert.cer");
	    
	    //create user pfx
	    KeyStore ks=this.genKS(pair, password.toCharArray(), cert, username,cert);
	    //save pfx of client
	    this.savePfx(ks, path, password,password+"userPfx.pfx");
	    
	    
    }
    
    
	
	public java.security.cert.X509Certificate getRoot_cert() {
		return root_cert;
	}


	public void setRoot_cert(java.security.cert.X509Certificate root_cert) {
		this.root_cert = root_cert;
	}


	public KeyStore getRoot_ks() {
		return root_ks;
	}


	public void setRoot_ks(KeyStore root_ks) {
		this.root_ks = root_ks;
	}

	public boolean sendCert(String toEmailAddress, String userName) throws IOException {
		
		StringBuffer sb = new StringBuffer();
	    this.readToBuffer(sb, user_cert_path);
	    String content=sb.toString();
		return email.sendEmailSendGrid("Girish", "Sundevil", toEmailAddress, fromEmail, "subj",
				"body", "Certificate.cer", content);
	}
	
	public boolean sendPfx(String toEmailAddress, String userName) {
		EmailService email = null;
		File cert=new File(user_pfx_path);
		return email.sendEmailSendGrid("Girish", "Sundevil", toEmailAddress, fromEmail, "subj",
				"body", user_pfx_path, "yo mama");
	}	

	public void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); 
        while (line != null) { 
            buffer.append(line); 
            buffer.append("\n"); 
            line = reader.readLine(); 
        }
        reader.close();
        is.close();
    }
	
	
	public void savePfx(KeyStore ks, String path, String pwd, String filename) 
			throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException{
		user_pfx_path="/"+filename;
		FileOutputStream fos = new FileOutputStream(user_pfx_path);
		ks.store(fos, pwd.toCharArray());
		fos.close();
	}
	
	public void saveCert(java.security.cert.X509Certificate cert, String path, String filename) 
			throws CertificateEncodingException, IOException{
		user_cert_path="/"+filename;
		FileOutputStream fos = new FileOutputStream(user_cert_path);
		fos.write(cert.getEncoded() );
		fos.flush();
		fos.close();

	}
	
	
	
	//generate key store with user's password in db
	public KeyStore genKS(KeyPair pair, char[] pwd, X509Certificate cert, String alias, X509Certificate root_cert) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(null, pwd);
		ks.setCertificateEntry(alias, cert);
		Certificate[] chain= new Certificate[1];
		chain[0]=root_cert;
		ks.setKeyEntry(alias, pair.getPrivate(), pwd, chain);
		return ks;
	}
	
	
	
	
	
	
	//generate keystore which can be used to create pfx file
	public KeyPair generateRSAKeyPair() throws Exception {
	    KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
	    kpGen.initialize(1024, new SecureRandom());
	    return kpGen.generateKeyPair();
	  }
	
	
	
	
	 @SuppressWarnings("deprecation")
	public X509Certificate generateV3Certificate(KeyPair pair, String username) throws Exception {
		
		 //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		 
		X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
		
		  certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		  
		  certGen.setIssuerDN(new X500Principal("CN=Richirich"));
		  certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
		  certGen.setNotAfter(new Date(System.currentTimeMillis() + 10000));
		  
		  String subDN="CN="+username;
		  
		  
		   //fill the signed username
		  certGen.setSubjectDN(new X500Principal(subDN));
		  certGen.setPublicKey(pair.getPublic());
		  certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
		
		  certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
		  certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature
		      | KeyUsage.keyEncipherment));
		  certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(
		      KeyPurposeId.id_kp_serverAuth));
		
		  certGen.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(
		      new GeneralName(GeneralName.rfc822Name, "test@test.test")));
		  
		  return certGen.generateX509Certificate(pair.getPrivate());
	}

	
	public boolean verifyCert(String cert_path, String username) throws Exception{
		FileInputStream f_input = new FileInputStream(cert_path);
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate cert;
    	//load certificate
    	cert=(X509Certificate)cf.generateCertificate(f_input);
    	//"2.5.29.32" is the oid of certificatePolicies
    	//byte[] subject_name=cert.getExtensionValue("2.5.29.32");
    	
    	//subject_name should be the signature
    	System.out.println("subjectDN:"+cert.getSubjectDN());
    	
    	username="CN="+username;
    	System.out.println("given username:"+username);
    	if(username.equals(cert.getSubjectDN().toString()))
    		return true;
    	else
    		return false;
	}
	
	@Override
	public String getEncryptedString(String userName, String plainText) throws Exception {
		byte[] encMsg;
		String publicKey= userDAO.getPubKey(userName);
		PublicKey pubKey =    KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey)));
		Cipher encryptMsg = Cipher.getInstance("RSA");
		Cipher decryptMsg = Cipher.getInstance("RSA");
		encryptMsg.init(Cipher.ENCRYPT_MODE, pubKey);
		encMsg = encryptMsg.doFinal(plainText.getBytes());
		String encryptedString = Base64.getEncoder().encodeToString(encMsg);
		return encryptedString;
		
	}
	
	
}
