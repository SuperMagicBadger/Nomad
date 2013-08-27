package Data;

/**
 * a resource counter.  Handles supply usage
 * per turn, etc;
 * @author Cow
 *
 */
public class Economy {
	//helpers--------------------------
	/**
	 * a class to manage the conversion of resource
	 * units to usable supplies.  meant to be shared between
	 * the same unit types to conserve memory
	 * @author Cow
	 *
	 */
	public class RUConverter{
		//conversion rates
		public float ruToAmmoRate = 1;
		public float ruToSuppliesRate = 1;
		public float ruToFuelRate = 1;
		
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
	//helpers==========================
	
	// varblok-------------------------
	//resources
	/** current resources */
	int resourceUnits;
	int ammo;
	int supplies;
	int fuel;
	//max counts
	/** resource ceilings */
	int maxResourceUnits;
	int maxAmmo;
	int maxSupplies;
	int maxFuel;
	//deltas
	/** the rate of RU change per turn*/
	public int deltaRU;
	public int deltaAmmo;
	public int deltaSupplies;
	public int deltaFuel;
	//converter
	public RUConverter converter;
	// varblok=========================
	
	// constructor---------------------
	public Economy(RUConverter converter){
		resourceUnits = ammo = supplies = fuel = 0;
		maxAmmo = maxFuel = maxResourceUnits = maxSupplies = -1;
		this.converter = converter;
	}
	
	public Economy(RUConverter converter, int resourceUnits, int ammo, int supplies, int fuel){
		this.resourceUnits = resourceUnits;
		this.ammo = ammo;
		this.supplies = supplies;
		this.fuel = fuel;
		maxAmmo = maxFuel = maxResourceUnits = maxSupplies = -1;
		this.converter = converter;
	}
	// constructor=====================
	
	// resource managers---------------
	/**
	 * 
	 * requests a certain amount of resources from the resource pool. the requested
	 * resources are removed from the pool.
	 * 
	 * @param ru the requested resources
	 * @return the resources pulled from the pool
	 */
	public int requestRU(int ru){
		ru = Math.min(ru, resourceUnits);
		resourceUnits -= ru;
		return ru;
	}
	
	public int requestAmmo(int a){
		a = Math.min(a, ammo);
		ammo -= a;
		return a;
	}
	
	public int requestSupplies(int supp){
		supp = Math.min(supp, supplies);
		supplies -= supp;
		return supp;
	}
	
	public int requestFuel(int f){
		f = Math.min(f, fuel);
		fuel -= f;
		return f;
	}
	
	/**
	 * Try and add the resource count to the resource pool. 
	 * 
	 * @param ru the resources to add to the pool
	 * @return the actual number added to the pool
	 */
	public int addRU(int ru){
		if(ru >= 0){
			ru = Math.min(ru, maxResourceUnits - resourceUnits);
		} else {
			ru = Math.max(ru, 0 - resourceUnits);
		}
		resourceUnits += ru;
		return ru;
	}
	public int addAmmo(int amm){
		if(amm >= 0){
			amm = Math.min(amm, maxAmmo - ammo);
		} else {
			amm = Math.max(amm, 0 - ammo);
		}
		ammo += amm;
		return amm;
	}
	public int addSupplies(int supp){
		if(supp >= 0){
			supp = Math.min(supp, maxSupplies - supplies);
		} else {
			supp = Math.max(supp, 0 - supplies);
		}
		supplies += supp;
		return supp;
	}
	public int addFuel(int f){
		if(f >= 0){
			f = Math.min(f, maxFuel - fuel);
		} else {
			f = Math.min(f, 0 - fuel);
		}
		f = Math.min(f, maxFuel - fuel);
		fuel += f;
		return f;
	}
	
	/**
	 * @return the current ru count
	 */
	public int getRU(){
		return resourceUnits;
	}
	public int getSupplies(){
		return supplies;
	}
	public int getAmmo(){
		return ammo;
	}
	public int getFuel(){
		return fuel;
	}
	// resource managers=====c==========
	
	// delta managers------------------
	/**
	 * apply deltas to the resources
	 */
	public void doDelta(){
		addRU(deltaRU);
		addSupplies(deltaSupplies);
		addAmmo(deltaAmmo);
		addFuel(deltaFuel);
	}
	
	/**
	 * set per turn change
	 * @param d change rate
	 */
	public void setRUDelta(int d){
		deltaRU = d;
	}
	
	public void setSupplyDelta(int d){
		deltaSupplies = d;
	}
	
	public void setAmmoDelta(int d){
		deltaAmmo = d;
	}
	
	public void setFuelDelta(int d){
		deltaFuel = d;
	}
	// delta managers==================
}
