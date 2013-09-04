package com.greatcow.nomad.data;

/**
 * a class to manage the conversion of resource
 * units to usable supplies.  meant to be shared between
 * the same unit types to conserve memory
 * @author Cow
 *
 */
public class RUConverter {
	//conversion rates
			public float ruToAmmoRate = 0.5f;
			public float ruToSuppliesRate = 1;
			public float ruToFuelRate = 0.25f;
			
			public float ruToAmmo(float ru){
				return ru * ruToAmmoRate;
			}
			public float ruToSupplies(float ru){
				return ru * ruToSuppliesRate;
			}
			public float ruToFuel(float ru){
				return ru * ruToFuelRate;
			}
}
