
public class SamsungCode {

	public static boolean validatePassword(String pass) {
        int a = pass.length();
        int b = 0;
        for (int i=0; i<a; i++) {
           b=(b<<8)+pass.charAt(i);
        }
        return a==4 && b==1263423320;
    }
	
	static public void main(String[] args) {
		int b = 0 ;
		for(int i = 65 ; i < 90; i++) {
			for(int j = 65 ; j < 90; j++) {
				for(int k = 65 ; k < 90; k++) {
					for(int l = 65 ; l < 90; l++) {
						b++; 
						String pass = "" + (char)i + (char)j + (char)k + (char)l;
						if(SamsungCode.validatePassword(pass)){
							System.out.println("Password obtained in : " + b + "attempts" );
							System.out.println("Password is: " + pass);
							System.exit(0);
						}
					}
				}
			}
		}
		
	}
	
}
