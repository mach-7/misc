package com.manchau.creational;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 * 
 * @author Maneesh
 * Item 1: Consider static factory methods instead of constructors
 * 
 * Why it's a good Idea:
 * 1. Declarative of intent and purpose for the constructed object
 * 2. Reuse of instance
 * 3. Polymorphism and abstraction of underlying implementations
 * 
 * Why not:
 * 1. Only having static factory methods prevents sub classing
 *  	- Although it might promote composition instead of inheritance
 * 2. In absence of proper nomenclature, these can be confusing for a user 
 */

interface ModifierService {
	public int doMod(int num);
}
class DoublerService implements ModifierService {
	@Override
	public int doMod(int num) {
		return num*2;
	}		
}
class SquarerService implements ModifierService {
	@Override
	public int doMod(int num) {
		return num*num;
	}		
}

interface ModifierServiceProvider {
	public ModifierService newModifierService(); 
}
class SquarerServiceProvider implements ModifierServiceProvider {
	private final SquarerService service;
	public SquarerServiceProvider() {
		service = new SquarerService();
	}
	@Override
	public ModifierService newModifierService() {
		return service;
	}		
}
class DoublerServiceProvider implements ModifierServiceProvider {
	private final DoublerService service;
	public DoublerServiceProvider() {
		service = new DoublerService();
	}
	@Override
	public ModifierService newModifierService() {
		return service;
	}		
}

class ModifierServices {
	/* registers service providers by name and provides static factory 
	   methods to get service instances */
	private static final Map<String,ModifierServiceProvider> providers =
			new ConcurrentHashMap<>();
	public static final String DEFAULT_PROVIDER_NAME = "<DEF>";
	
	//Registration API
	public static void registerProvider(String name, ModifierServiceProvider provider) {
		providers.put(name, provider);
	}
	//Static factories
	public static ModifierService newInstance() {
		return newInstance(DEFAULT_PROVIDER_NAME);
	}
	public static ModifierService newInstance(String id) {
		ModifierServiceProvider provider = providers.get(id);
		if(provider == null) {
			throw new IllegalArgumentException("No provider registered with the name: " + id);				
		}
		return provider.newModifierService();
	}
}

public class StaticFactoryDemo {
	public static void main(String[] args) {
		int i = 10;
		String name = "Squarer";
		// register service providers
		ModifierServices.registerProvider(ModifierServices.DEFAULT_PROVIDER_NAME, new DoublerServiceProvider());
		ModifierServices.registerProvider(name, new SquarerServiceProvider());
		
		// retrieve different modifications based on different names of service providers
		
		System.out.println(ModifierServices.newInstance().doMod(i));
		System.out.println(ModifierServices.newInstance(name).doMod(i));
	}
}
