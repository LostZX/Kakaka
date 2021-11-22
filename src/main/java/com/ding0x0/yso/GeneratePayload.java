package com.ding0x0.yso;

import com.ding0x0.yso.payloads.ObjectPayload;
import com.ding0x0.yso.payloads.ObjectPayload.Utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;


@SuppressWarnings("rawtypes")
public class GeneratePayload {
	private static final int INTERNAL_ERROR_CODE = 70;
	private static final int USAGE_CODE = 64;

	public static void generatePayload(String payloadType, String command){
		final Class<? extends ObjectPayload> payloadClass = Utils.getPayloadClass(payloadType);

		if (payloadClass == null) {
			System.err.println("Invalid payload type '" + payloadType + "'");
			System.exit(USAGE_CODE);
			return; // make null analysis happy
		}

		try {
			final ObjectPayload payload = payloadClass.newInstance();
			final Object object = payload.getObject(command);
			Serializer.serialize(object);
			ObjectPayload.Utils.releasePayload(payload, object);
		} catch (Throwable e) {
			System.err.println("Error while generating or serializing payload");
			e.printStackTrace();
			System.exit(INTERNAL_ERROR_CODE);
		}
	}
}
