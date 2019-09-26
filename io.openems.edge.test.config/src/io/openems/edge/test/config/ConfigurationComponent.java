package io.openems.edge.test.config;


import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.OpenemsComponent;

/**
 * This controller prints information about all available components on the
 * console.
 */
@Designate(ocd = Config.class, factory = true)
@Component(name = "ConfigurationComponent", immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConfigurationComponent extends AbstractOpenemsComponent implements OpenemsComponent {

	private final Logger log = LoggerFactory.getLogger(ConfigurationComponent.class);

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

	public ConfigurationComponent() {
		super(//
				OpenemsComponent.ChannelId.values()  //
		);
	}

	@Activate
	void activate(ComponentContext context, Config config) {
		log.info("activate()");
		super.activate(context, config.id(), "", true);
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

}
