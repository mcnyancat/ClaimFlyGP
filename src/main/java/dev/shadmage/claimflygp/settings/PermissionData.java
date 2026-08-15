package dev.shadmage.claimflygp.settings;

import org.mineacademy.fo.command.annotation.Permission;
import org.mineacademy.fo.command.annotation.PermissionGroup;

public class PermissionData {
	@Permission
	public static final String PERMISSION_CLAIMFLY_USE = "claimfly.use";
	@Permission
	public static final String PERMISSION_CLAIMFLY_ADMIN = "claimfly.claims.admin";
	@Permission
	public static final String PERMISSION_CLAIMFLY_OTHERS = "claimfly.claims.others";
	@Permission
	public static final String PERMISSION_CLAIMFLY_UNCLAIMED = "claimfly.claims.unclaimed";
	@Permission
	public static final String PERMISSION_CLAIMFLY_BYPASS = "claimfly.claims.bypass";

	@PermissionGroup
	public static class Admin{
		@Permission
		public static final String PERMISSION_CLAIMFLY_RELOAD = "claimfly.admin.reload";
		@Permission
		public static final String PERMISSION_CLAIMFLY_PERMS = "claimfly.admin.permissions";
		@Permission
		public static final String PERMISSION_CLAIMFLY_STATUS = "claimfly.admin.status";
		@Permission
		public static final String PERMISSION_CLAIMFLY_INSPECT = "claimfly.admin.inspect";
	}

}
