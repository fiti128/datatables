package rw.props.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity(name = "PROPERTIES")
public class MyEntry {
	@Id @Size(max = 50) @Column(name="PROPERTY_KEY")
	private String key;
	@Size(max = 50) @Column(name="PROPERTY_VALUE")
	private String value;

    public MyEntry() {
    }

    public MyEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public static MyEntry parse(String buffer) {
		String[] array = buffer.split("=", 2);
		return new MyEntry(array[0],array[1]);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyEntry myEntry = (MyEntry) o;

        if (!key.equals(myEntry.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return key + '=' + value;
    }
}
