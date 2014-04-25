package com.song7749.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.castor.util.Base64Decoder;
import org.castor.util.Base64Encoder;


public class ObjectSerializeUtil {
	// Read the object from Base64 string.
	public static Object fromString(String s) throws IOException,
			ClassNotFoundException {
		byte[] data = Base64Decoder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
				data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

	// Write the object to a Base64 string.
	public static String toString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return new String(Base64Encoder.encode(baos.toByteArray()));
	}
}
