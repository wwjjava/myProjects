package ztzb.com.tongtech.gtp;

import java.util.UUID;

public class UUIDUtil {
	public static String getUUID(){
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "");
		//return uuid.toString();
	}
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
