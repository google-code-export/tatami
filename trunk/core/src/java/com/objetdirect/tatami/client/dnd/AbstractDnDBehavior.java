package com.objetdirect.tatami.client.dnd;

import java.util.Collection;

public abstract class AbstractDnDBehavior<E extends IDnDElement,S extends IDnDSource<? extends E>,T extends IDnDTarget> implements IDnDBehavior<E, S, T>{

	public boolean checkItemAcceptance(S source, T target,
			Collection<E> dndElements) {
		return true;
	}

	public void dragOver(IDnDTarget target) {
	}

	public void elementsAccepted(S source, T target, Collection<E> elements,
			boolean copied, IDnDController<?, T> controller) {
	}

	public void onCancel() {
	}

	public void onDndStart(Collection<E> elementBeingDragged, S source,
			boolean ctrlKey) {
	}

	public boolean onDrop(Collection<E> dndElements, S source, T target,
			String targetNodeId, boolean isCopy) {
		return true;
	}

}
