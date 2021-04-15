package io.openems.edge.common.user;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import io.openems.common.exceptions.OpenemsError.OpenemsNamedException;
import io.openems.common.session.AbstractUser;
import io.openems.common.session.Role;
import io.openems.common.utils.JsonUtils;

/**
 * A {@link User} used by OpenEMS Edge.
 */
public class User extends AbstractUser {

	/**
	 * Default-Edge-ID for User.
	 */
	public final static String DEFAULT_EDGE_ID = "0";

	/**
	 * Constructs an {@link User}.
	 * 
	 * @param id   the User-ID
	 * @param name the name
	 * @param role the {@link Role}; used as global Role and assigned to
	 *             {@link User#DEFAULT_EDGE_ID}.
	 */
	protected User(String id, String name, Role role) {
		super(id, name, role, Maps.newTreeMap(ImmutableSortedMap.of(DEFAULT_EDGE_ID, role)));
	}

	/**
	 * Gets the Role (Global and Per-Edge-Role are the same for OpenEMS Edge
	 * {@link User}).
	 * 
	 * @param edgeId the Edge-ID
	 * @return the {@link Role}
	 */
	public Role getRole() {
		return super.getGlobalRole();
	}

	/**
	 * Throws an exception if the Role (Global and Per-Edge-Role are the same for
	 * OpenEMS Edge {@link User}) is equal or more privileged than the given Role.
	 * 
	 * @param resource a resource identifier; used for the exception
	 * @param role     the compared {@link Role}
	 * @throws OpenemsNamedException if the global Role privileges are less
	 */
	public void assertRoleIsAtLeast(String resource, Role role) throws OpenemsNamedException {
		this.getGlobalRole().assertIsAtLeast(resource, role);
	}

	/**
	 * Parses a {@link JsonObject} to a User.
	 * 
	 * <pre>
	 * {
	 *   "id": string,
	 *   "name: string,
	 *   "role": string
	 * }
	 * </pre>
	 * 
	 * @param j the {@link JsonObject}
	 * @return a {@link User}
	 * @throws OpenemsNamedException on error
	 */
	public static User from(JsonObject j) throws OpenemsNamedException {
		String id = JsonUtils.getAsString(j, "id");
		String name = JsonUtils.getAsString(j, "name");
		Role role = Role.getRole(JsonUtils.getAsString(j, "role"));
		return new User(id, name, role);
	}
}
