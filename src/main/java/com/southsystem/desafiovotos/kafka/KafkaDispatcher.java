package com.southsystem.desafiovotos.kafka;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

@Component
public class KafkaDispatcher<T> implements Closeable {

	private KafkaProducer<String, Message<T>> produtor;

	public KafkaDispatcher() {
		this.produtor = new KafkaProducer<>(properties());
	}

	public void send(String topico, String key, T payload) throws InterruptedException, ExecutionException {

		Message<T> valor = new Message<>(new CorrelationId(), payload);

		ProducerRecord<String, Message<T>> registro = new ProducerRecord<>(topico, key, valor);

		Callback callback = (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
				return;
			}

			System.out.println(
					"topic " + data.topic() + "::: partition" + data.partition() + ":::: offset: " + data.offset());

		};

		produtor.send(registro, callback).get();

	}

	private Properties properties() {

		Properties propriedades = new Properties();

		propriedades.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		propriedades.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		propriedades.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

		return propriedades;
	}

	@Override
	public void close() {
		produtor.close();
	}

}
