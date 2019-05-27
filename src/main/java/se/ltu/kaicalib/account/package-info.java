/**
 * Provides the classes necessary to implement a level of Spring Seurity.
 *
 * It is angled towards providing authentication for an online library system
 * and thus presents roles Patron and Admin.
 *
 * It is configured to use a dedicated physical persistence level and the
 * implementation is left to the user whether that should be schema level or
 * further.
 *
 * This package is currently not distributed independently of the Kaicalib Core.
 * Coupling is low, but the primary aspects to be finalised are:
 *
 * -dedicated (sample) set of html pages to demonstrate usage.
 *
 * - Configuration of the datasource is currently done by the core service, but that should
 * override a default configuration implementation provided with the package.
 *
 *
 * @author
 */
package se.ltu.kaicalib.account;
