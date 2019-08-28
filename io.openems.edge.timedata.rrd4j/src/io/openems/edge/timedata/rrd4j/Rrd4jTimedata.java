package io.openems.edge.timedata.rrd4j;

import java.net.URI;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.metatype.annotations.Designate;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.types.ChannelAddress;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.timedata.api.Timedata;

/**
 * Provides read and write access to InfluxDB.
 */
@Designate(ocd = Config.class, factory = true)
@Component(name = "Timedata.Rrd4j", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE, //
		property = EventConstants.EVENT_TOPIC + "=" + EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE)
public class Rrd4jTimedata extends AbstractOpenemsComponent implements Timedata, OpenemsComponent, EventHandler {

	private final static String DATA_DIRECTORY = "rrd4j";

	private final Logger log = LoggerFactory.getLogger(Rrd4jTimedata.class);

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		;
		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}

	public Rrd4jTimedata() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				Timedata.ChannelId.values(), //
				ChannelId.values() //
		);
	}

	@Reference
	protected ComponentManager componentManager;

	@Activate
	void activate(ComponentContext context, Config config) {
		super.activate(context, config.id(), config.alias(), config.enabled());
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	public void handleEvent(Event event) {
		if (!this.isEnabled()) {
			return;
		}
		switch (event.getTopic()) {
		case EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE:
			this.collectAndWriteChannelValues();
			break;
		}
	}

	protected synchronized void collectAndWriteChannelValues() {
//		this.getDatabase(channelAddress)
		// TODO
		
//		long timestamp = System.currentTimeMillis() / 1000;
//		final Builder point = Point.measurement(InfluxConnector.MEASUREMENT).time(timestamp, TimeUnit.SECONDS);
//		final AtomicBoolean addedAtLeastOneChannelValue = new AtomicBoolean(false);
//
//		this.componentManager.getEnabledComponents().stream().filter(c -> c.isEnabled()).forEach(component -> {
//			component.channels().forEach(channel -> {
//				Optional<?> valueOpt = channel.value().asOptional();
//				if (!valueOpt.isPresent()) {
//					// ignore not available channels
//					return;
//				}
//				Object value = valueOpt.get();
//				String address = channel.address().toString();
//				try {
//					switch (channel.getType()) {
//					case BOOLEAN:
//						point.addField(address, ((Boolean) value ? 1 : 0));
//						break;
//					case SHORT:
//						point.addField(address, (Short) value);
//						break;
//					case INTEGER:
//						point.addField(address, (Integer) value);
//						break;
//					case LONG:
//						point.addField(address, (Long) value);
//						break;
//					case FLOAT:
//						point.addField(address, (Float) value);
//						break;
//					case DOUBLE:
//						point.addField(address, (Double) value);
//						break;
//					case STRING:
//						point.addField(address, (String) value);
//						break;
//					}
//				} catch (IllegalArgumentException e) {
//					this.log.warn("Unable to add Channel [" + address + "] value [" + value + "]: " + e.getMessage());
//					return;
//				}
//				addedAtLeastOneChannelValue.set(true);
//			});
//		});
//
//		if (addedAtLeastOneChannelValue.get()) {
//			try {
//				this.influxConnector.write(point.build());
//			} catch (OpenemsException e) {
//				this.logError(this.log, e.getMessage());
//			}
//		}
	}

	@Override
	public SortedMap<ZonedDateTime, SortedMap<ChannelAddress, JsonElement>> queryHistoricData(String edgeId,
			ZonedDateTime fromDate, ZonedDateTime toDate, Set<ChannelAddress> channels, int resolution)
			throws OpenemsNamedException {
		// ignore edgeId as Points are also written without Edge-ID
		Optional<Integer> influxEdgeId = Optional.empty();
//		return this.influxConnector.queryHistoricData(influxEdgeId, fromDate, toDate, channels, resolution);
		return null;
	}

	@Override
	public SortedMap<ChannelAddress, JsonElement> queryHistoricEnergy(String edgeId, ZonedDateTime fromDate,
			ZonedDateTime toDate, Set<ChannelAddress> channels) throws OpenemsNamedException {
		// ignore edgeId as Points are also written without Edge-ID
		Optional<Integer> influxEdgeId = Optional.empty();
//		return this.influxConnector.queryHistoricEnergy(influxEdgeId, fromDate, toDate, channels);
		return null;
	}

	private final RrdDb getDatabase(ChannelAddress channelAddress) {
		this.getFilePath(channelAddress);
		// TODO
		return null;

//		RrdDef rrdDef = new RrdDef(rrdPath, 1);
//		rrdDef.setStartTime(-1);
//		rrdDef.addDatasource("_sum/EssSoc", DsType.GAUGE, 1, Double.NaN, Double.NaN);
//		rrdDef.addDatasource("_sum/EssActivePower", DsType.GAUGE, 1, Double.NaN, Double.NaN);
//		rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 1, 600); // 1 step, 600 rows
//		rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 6, 700); // 6 steps, 700 rows
//		rrdDef.addArchive(ConsolFun.MAX, 0.5, 1, 600);
	}

	/**
	 * Gets the path for the RRD4j file for the given Channel-Address.
	 * 
	 * @param channelAddress the Channel-Address
	 * @return the path as URI
	 */
	private final URI getFilePath(ChannelAddress channelAddress) {
		return Paths.get(//
				Optional.ofNullable(System.getProperty("openems.data.dir")).orElse(""), //
				DATA_DIRECTORY, //
				channelAddress.getComponentId().toString(), //
				channelAddress.getChannelId().toString()) //
				.toUri();
	}
}
