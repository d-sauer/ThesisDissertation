keytool -genkey -alias tomcat -keyalg RSA -sigalg SHA1withRSA -keypass changeit -storepass changeit -keystore ./servIdentity.jks -dname "cn=localhost"