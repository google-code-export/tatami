/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.client.dnd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScope.Couple;


/**
 * This class is a static DnDBehavior registry
 * It comprises register and unregister methods for applying IDnDBehavior 
 * to specific IDnDSource or IDnDTargets 
 * 
 * 
 * @author rdunklau
 *
 */
public class DnDBehaviors {

	/**
	 * A behavior scope exception is thrown when a behavior cannot be registered for 
	 * the given source and target, usually because another behavior was already registered.
	 * 
	 * @author rdunklau
	 *
	 */
	@SuppressWarnings("serial")
	public static class BehaviorScopeException extends Exception{
		public BehaviorScopeException() {
			super();
		}
		public BehaviorScopeException(String message) {
			super(message);
		}
	}
	
	
	/**
	 * A behavior scope associates IDnDBehavior to one or more IDnDSource and an IDnDTarget couples.
	 * 
	 * @author rdunklau
	 *
	 */
	static class BehaviorScope{
		
		/**
		 * The couple class defines a source-target couple
		 * 
		 * @author rdunklau
		 *
		 */
		static class Couple implements Comparable<Couple>{
			
			private IDnDSource<?> source;
			
			private IDnDTarget target;
			
			/**
			 * @return the source from this source-target couple
			 */
			public IDnDSource<?> getSource() {
				return source;
			}

			/**
			 * @param source  the source from this source-target couple
			 */
			public void setSource(IDnDSource<?> source) {
				this.source = source;
			}

			/**
			 * @return the target from this source-target couple
			 */
			public IDnDTarget getTarget() {
				return target;
			}

			/**
			 * @param target the target from this source-target couple
			 */
			public void setTarget(IDnDTarget target) {
				this.target = target;
			}

			/**
			 * @param source : the source from this source-target couple
			 * @param target : the target from this source-target couple
			 */
			public Couple(IDnDSource<?> source, IDnDTarget target) {
				super();
				this.source = source;
				this.target = target;
			}

			public Couple(){
				
			}
			
			/**
			 * @param source : a source
			 * @param target : a target
			 * @return true if the couple matches the source and target, false otherwise
			 */
			public boolean matchCouple(IDnDSource<?> source, IDnDTarget target){
				if(this.source == source && this.target == target){
					return true;
				}else if(this.source == null && this.target == null){
					return true;
				}else if(this.source == null ){
					if(source != null && (this.target == target || target == null)){
						return true;
					}else{
						return false;
					}
				}else if(this.target == null){
					if(target != null && (this.source == source || source == null)){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
			
			public boolean isAmbiguousWith(IDnDSource<?> source, IDnDTarget target){
				if(this.source == source && (this.target == target || this.target == null || target == null)){
					return true;
				}
				if(this.target == target && (this.source == source || this.source == null || source == null)){
					return true;
				}
				return false;
			}
			
			/**
			 * @param source
			 * @param target
			 * @return true if the couple matches exactly the source and target 
			 */
			public boolean matchExactly(IDnDSource<?> source, IDnDTarget target){
				if(this.source == source && this.target == target){
					return true;
				}
				return false;
			}

			
			//Rules to compare two couples based on the number of 
			//the couple components which are null
			public int compareTo(Couple o) {
				if(o == null){
					return Integer.MAX_VALUE;
				}
				boolean thisSourceNull = this.getSource() == null;
				boolean thisTargetNull = this.getTarget() == null;
				boolean oSourceNull = o.getSource() == null;
				boolean oTargetNull = o.getTarget() == null;
				int nbNullsForThis = (thisSourceNull ? 1 : 0) + (thisTargetNull ? 1 : 0); 
				int nbNullsForO = (oSourceNull ? 1 : 0) + (oTargetNull ? 1 : 0);
				return (nbNullsForO - nbNullsForThis);
			}
			
		}
		
		/**
		 * A collection of couple to which the behavior should be applied
		 */
		private Collection<Couple> couples = new ArrayList<Couple>();
		
		public Collection<Couple> getCouples() {
			return couples;
		}

		public void setCouples(Collection<Couple> couples) {
			this.couples = couples;
		}

		public void setBehavior(IDnDBehavior<?, ?, ?> behavior) {
			this.behavior = behavior;
		}

		/**
		 * The behavior which scopes is defined  
		 */
		private IDnDBehavior<?,?,?> behavior;
		
		/**
		 * @return The behavior which scopes is defined  
		 */
		public IDnDBehavior<?,?,?> getBehavior() {
			return behavior;
		}

		/**
		 * Wether the behavior is enabled or not
		 */
		private boolean enabled = true;
		
		/**
		 * Adds a Source/Target couple to this behavior's scope
		 * 
		 * @param source
		 * @param target
		 */
		private   void addCouple(IDnDSource<? extends IDnDElement> source , IDnDTarget target) throws BehaviorScopeException{
			couples.add(new Couple(source, target));
		}
		
		/**
		 * @param behavior The behavior which scopes is defined  
		 */
		public BehaviorScope(
				IDnDBehavior<?,?,?> behavior) {
			super();
			this.behavior = behavior;
		}

		/**
		 * Removes a Source/Target couple to this behavior's scope
		 * 
		 * @param source
		 * @param target
		 */
		private void removeCouple(IDnDSource<? extends IDnDElement> source , IDnDTarget target) {
			for (Couple couple : couples) {
				if(couple.getSource() ==  source && couple.getTarget() == target){
					couples.remove(couple);
				}
			}
		}

		/**
		 * @param source
		 * @param target
		 * @return true if the behavior is applied for the source/target couple
		 */
		public boolean isAppliedFor(IDnDSource<?> source , IDnDTarget target){
			for (Couple couple : couples) {
				if(couple.matchCouple(source, target)){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * @param source
		 * @param target
		 * @return true if the behavior is applied exactly for this source/target couple (and not
		 * through a null/target,source/null or null/null couple)
		 */
		public boolean isAppliedExactlyFor(IDnDSource<?> source , IDnDTarget target){
			for (Couple couple : couples) {
				if(couple.matchExactly(source, target)){
					return true;
				}
			}
			return false;
		}
		
		public Couple getBestMatchingCouple(IDnDSource<?> source , IDnDTarget target){
			List<Couple> matchingcouples = new ArrayList<Couple>();
			for(Couple couple : couples){
				if(couple.matchCouple(source, target)){
					matchingcouples.add(couple);
				}
			}
			if(matchingcouples.size() == 0){
				return null;
			}
			if(matchingcouples.size() > 1){
				Collections.sort(matchingcouples);
			}
			return matchingcouples.get(0);
		}
		
		public Couple getAnyAmbiguousCouple(IDnDSource<?> source , IDnDTarget target){
			List<Couple> matchingcouples = new ArrayList<Couple>();
			for(Couple couple : couples){
				if(couple.matchCouple(source, target)){
					matchingcouples.add(couple);
				}else{
					if(couple.isAmbiguousWith(source, target)){
						matchingcouples.add(couple);
					}
				}
			}
			if(matchingcouples.size() == 0){
				return null;
			}
			if(matchingcouples.size() > 1){
				Collections.sort(matchingcouples);
			}
			return matchingcouples.get(0);
		}
		
		/**
		 * @return true if this behavior is enabled
		 */
		public boolean isEnabled() {
			return enabled;
		}
		
		/**
		 * @param enabled : wether the behavior should be enabled
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		
		
	}
	
	/**
	 * A map containing the associations between behaviors and ther behaviorScope
	 */
	static Map<IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget>, BehaviorScope> behaviorScopesMapping = new HashMap<IDnDBehavior<? extends IDnDElement,? extends IDnDSource<?>,? extends IDnDTarget>, BehaviorScope>();
	
	/**
	 * Adds a scope to a behavior
	 * 
	 * @param <E>
	 * @param <S>
	 * @param <T>
	 * @param behavior
	 * @param source
	 * @param target
	 * @throws BehaviorScopeException
	 */
	public static <E extends IDnDElement , S extends IDnDSource<? extends  E> , T extends IDnDTarget> void addScopeToBehavior(IDnDBehavior<E, S, T> behavior,S source , T target) throws BehaviorScopeException{
		BehaviorScope scope = behaviorScopesMapping.get(behavior);
		if(scope == null){
			scope = new BehaviorScope(behavior);
			behaviorScopesMapping.put(behavior,scope);
		}
		Couple couple = new Couple(source,target);
		Couple bestCouple = null;
		for(BehaviorScope scopeinspected : behaviorScopesMapping.values()){
			Couple bestCoupleCandidate = scopeinspected.getAnyAmbiguousCouple(source, target);
			if(bestCoupleCandidate != null){
				if(bestCoupleCandidate.matchExactly(source, target)){
					throw new BehaviorScopeException("A behavior is already defined for this couple");
				}
				if(bestCouple == null){
					bestCouple = bestCoupleCandidate;
				}else{
					if(bestCouple.compareTo(bestCoupleCandidate) < 0){
						bestCouple = bestCoupleCandidate;
					}
				}
			}
		}
		if(bestCouple != null){
			if(source == null){
				if(bestCouple.getTarget() == null){
					throwBehaviorScopeConflictException(bestCouple.getSource(), bestCouple.getTarget(), source , target, true);
				}
			}else if(target == null){
				if(bestCouple.getSource() == null){
					throwBehaviorScopeConflictException(bestCouple.getSource(), bestCouple.getTarget(), source , target, false);
				}
			}
		}
		scope.addCouple(source, target);
	}
	
	private static void throwBehaviorScopeConflictException(IDnDSource<?> dnDSource,  IDnDTarget existingTarget, IDnDSource<?> source, IDnDTarget definedTarget , boolean existingSourceDefinedTargetAmbiguous) throws BehaviorScopeException{
		if(existingSourceDefinedTargetAmbiguous){
			throw new BehaviorScopeException("The scope defined (source = " + source + " target = " + definedTarget + ") conflicts with (source = " + dnDSource + " target = " + existingTarget + "). You should define which behavior to apply for source = " + dnDSource + " target = " + definedTarget );
		}else{
			throw new BehaviorScopeException("The scope defined (source = " + source + " target = " + definedTarget + ") conflicts with (source = " + dnDSource + " target = " + existingTarget + "). You should define which behavior to apply for source = " + existingTarget + " target = " + source );
		}
		
	}
	
	public static BehaviorScope getBehaviorScopeFor(IDnDSource<?> source, IDnDTarget target){
		Collection<BehaviorScope> scopes = behaviorScopesMapping.values();
		Collection<BehaviorScope> appliedBehavior = new ArrayList<BehaviorScope>();
		for (BehaviorScope behaviorScope : scopes) {
			if(behaviorScope.isAppliedFor(source, target)){
				appliedBehavior.add(behaviorScope);
			}
		}
		if(appliedBehavior.size() > 0){
			BehaviorScope bestMatching = null;
			for (BehaviorScope behaviorScope : appliedBehavior) {
				if(bestMatching == null){
					bestMatching = behaviorScope;
				}else{
					if(behaviorScope.getBestMatchingCouple(source, target).compareTo(bestMatching.getBestMatchingCouple(source, target)) > 0){
						bestMatching = behaviorScope; 
					}
				}
			}
			return bestMatching;
		}
		return null;
	}
	
	/**
	 * Remove a scope from a behavior
	 * 
	 * @param <E>
	 * @param <S>
	 * @param <T>
	 * @param behavior
	 * @param source
	 * @param target
	 */
	 public static <E extends IDnDElement , S extends IDnDSource<? extends  E> , T extends IDnDTarget> void removeScopeFromBehavior(IDnDBehavior<E, S, T> behavior,S source , T target){
		BehaviorScope scope = behaviorScopesMapping.get(behavior);
		if(scope == null){
			return;
		}
		scope.removeCouple(source, target);
	}
	
	/**
	 * @param source
	 * @param target
	 * @return : the behavior which should be applied to this source and target couple
	 */
	public static IDnDBehavior<?,?,?> getBehaviorFor(IDnDSource<?> source, IDnDTarget target){
		BehaviorScope scope = getBehaviorScopeFor(source, target);
		if(scope != null){
			return scope.getBehavior();
		}else{
			return null;
		}
	}
	
	public static Collection<IDnDBehavior<? extends IDnDElement,? extends IDnDSource<?>,? extends IDnDTarget>> getAllBehavior(){
		return behaviorScopesMapping.keySet();
	}
	
	public static void removeAllBehaviors(){
		Set<IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget>> scopes = behaviorScopesMapping.keySet();
		for (Iterator<IDnDBehavior<? extends IDnDElement, ? extends IDnDSource<?>, ? extends IDnDTarget>> iterator = scopes.iterator(); iterator.hasNext();) {
			iterator.next();
			iterator.remove();
		}
	}
	
	public static Collection<IDnDBehavior<?, ?, ?>> getAllBehaviorForSource(IDnDSource<?> source){
		Collection<BehaviorScope> scopes = behaviorScopesMapping.values();
		Collection<IDnDBehavior<?, ?,?>> appliedBehavior = new ArrayList<IDnDBehavior<?,?,?>>();
		for (BehaviorScope behaviorScope : scopes) {
			for(Couple couple : behaviorScope.getCouples()){
				if(couple.getSource() == source || couple.getSource() == null){
					appliedBehavior.add(behaviorScope.getBehavior());
					break;
				}
			}
		}
		return appliedBehavior;
	}
	
	public static Collection<IDnDBehavior<?, ?, ?>> getAllBehaviorForTarget(IDnDTarget target){
		Collection<BehaviorScope> scopes = behaviorScopesMapping.values();
		Collection<IDnDBehavior<?, ?,?>> appliedBehavior = new ArrayList<IDnDBehavior<?,?,?>>();
		
		for (BehaviorScope behaviorScope : scopes) {
			for(Couple couple : behaviorScope.getCouples()){
				if(couple.getTarget() == target || couple.getTarget() == null){
					appliedBehavior.add(behaviorScope.getBehavior());
					break;
				}
			}
		}
		return appliedBehavior;
	}
	
	
}
