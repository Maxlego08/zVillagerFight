package fr.maxlego08.mobfighter.api.addons;

public interface AddonDescription {

	/**
	 * Get author of the addon
	 * 
	 * @return author
	 */
	public String getAuthor();

	/**
	 * Get addon version
	 * 
	 * @return version
	 */
	public String getVersion();

	/**
	 * Get addon name
	 * 
	 * @return name
	 */
	public String getName();

	/**
	 * Get main class path
	 * 
	 * @return main class path
	 */
	public String getMain();

}
