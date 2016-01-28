import java.io.Serializable;

public class Data implements Serializable {
	public static final int NEWUSER = 0, WHOISIN = 1, MESSAGE = 2, LOGOUT = 3;
	private static final long serialVersionUID = 1L;
	private int type;
	private String msg;

	public Data(int type, String msg) {
		this.type=type;
		this.msg=msg;
	}
	public String getMsg() {
		return msg;
	}
	public int getType() {
		return type;
	}
	public boolean equals(Object o) {
		if (o instanceof Data && type==((Data)o).type && msg.equals(((Data)o).msg))
			return true;
		return false;
	}
	public String toString() {
		return "type:"+type+" message:"+msg;
	}
}
