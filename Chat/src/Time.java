import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
	private Date localTime;
	

	public Time() {
		localTime = new Date(System.currentTimeMillis());
	}
	public String hoursFormat() {
		return (new SimpleDateFormat("HH:mm:ss")).format(localTime);
	}
	public String yearsFormat() {
		return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(localTime);
	}
	public String regolarFormat() {
		return localTime.toString();
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Time && localTime==((Time)o).localTime)
			return true;
		return false;
	}
	@Override
	public String toString()
	{
		return localTime.toString();
	}
}
