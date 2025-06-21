package com.birthday.video.maker.Birthday_Video.customgallery.hlistview.widget;

import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.HeterogeneousExpandableList;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;


class ExpandableHListConnector extends BaseAdapter implements Filterable {

	private ExpandableListAdapter mExpandableListAdapter;

	private ArrayList<GroupMetadata> mExpGroupMetadataList;

	private int mTotalExpChildrenCount;

	/** Change observer used to have ExpandableListAdapter changes pushed to us */
	private final DataSetObserver mDataSetObserver = new MyDataSetObserver();

	/**
	 * Constructs the connector
	 */
	public ExpandableHListConnector( ExpandableListAdapter expandableListAdapter ) {
		mExpGroupMetadataList = new ArrayList<>();

		setExpandableListAdapter(expandableListAdapter);
	}

	/**
	 * Point to the {@link ExpandableListAdapter} that will give us data/Views
	 *
	 * @param expandableListAdapter the adapter that supplies us with data/Views
	 */
	public void setExpandableListAdapter( ExpandableListAdapter expandableListAdapter ) {
		if( mExpandableListAdapter != null ) {
			mExpandableListAdapter.unregisterDataSetObserver( mDataSetObserver );
		}

		mExpandableListAdapter = expandableListAdapter;
		expandableListAdapter.registerDataSetObserver( mDataSetObserver );
	}

	PositionMetadata getUnflattenedPos( final int flPos ) {
	    /* Keep locally since frequent use */
		final ArrayList<GroupMetadata> egml = mExpGroupMetadataList;
		final int numExpGroups = egml.size();

        /* Binary search variables */
		int leftExpGroupIndex = 0;
		int rightExpGroupIndex = numExpGroups - 1;
		int midExpGroupIndex = 0;
		GroupMetadata midExpGm;

		if( numExpGroups == 0 ) {
            /*
             * There aren't any expanded groups (hence no visible children
             * either), so flPos must be a group and its group pos will be the
             * same as its flPos
             */
			return PositionMetadata.obtain(flPos, ExpandableHListPosition.GROUP, flPos, -1, null, 0);
		}


		while( leftExpGroupIndex <= rightExpGroupIndex ) {
			midExpGroupIndex = ( rightExpGroupIndex - leftExpGroupIndex ) / 2 + leftExpGroupIndex;
			midExpGm = egml.get( midExpGroupIndex );

			if( flPos > midExpGm.lastChildFlPos ) {
                /*
                 * The flat list position is after the current middle group's
                 * last child's flat list position, so search right
                 */
				leftExpGroupIndex = midExpGroupIndex + 1;
			}
			else if( flPos < midExpGm.flPos ) {
                /*
                 * The flat list position is before the current middle group's
                 * flat list position, so search left
                 */
				rightExpGroupIndex = midExpGroupIndex - 1;
			}
			else if( flPos == midExpGm.flPos ) {
                /*
                 * The flat list position is this middle group's flat list
                 * position, so we've found an exact hit
                 */
				return PositionMetadata.obtain(flPos, ExpandableHListPosition.GROUP, midExpGm.gPos, -1, midExpGm, midExpGroupIndex);
			}
			else if( flPos <= midExpGm.lastChildFlPos
                    /* && flPos > midGm.flPos as deduced from previous
                     * conditions */ ) {
                /* The flat list position is a child of the middle group */

                /*
                 * Subtract the first child's flat list position from the
                 * specified flat list pos to get the child's position within
                 * the group
                 */
				final int childPos = flPos - ( midExpGm.flPos + 1 );
				return PositionMetadata.obtain(flPos, ExpandableHListPosition.CHILD, midExpGm.gPos, childPos, midExpGm, midExpGroupIndex);
			}
		}


		int insertPosition;

		/** What is its group position in the list of all groups? */
		int groupPos;

		if( leftExpGroupIndex > midExpGroupIndex ) {


			final GroupMetadata leftExpGm = egml.get( leftExpGroupIndex - 1 );

			insertPosition = leftExpGroupIndex;

			groupPos = ( flPos - leftExpGm.lastChildFlPos ) + leftExpGm.gPos;
		}
		else if( rightExpGroupIndex < midExpGroupIndex ) {


			final GroupMetadata rightExpGm = egml.get( ++ rightExpGroupIndex );

			insertPosition = rightExpGroupIndex;


			groupPos = rightExpGm.gPos - ( rightExpGm.flPos - flPos );
		}
		else {
			throw new RuntimeException( "Unknown state" );
		}

		return PositionMetadata.obtain(flPos, ExpandableHListPosition.GROUP, groupPos, -1, null, insertPosition);
	}


	PositionMetadata getFlattenedPos( final ExpandableHListPosition pos ) {
		final ArrayList<GroupMetadata> egml = mExpGroupMetadataList;
		final int numExpGroups = egml.size();

        /* Binary search variables */
		int leftExpGroupIndex = 0;
		int rightExpGroupIndex = numExpGroups - 1;
		int midExpGroupIndex = 0;
		GroupMetadata midExpGm;

		if( numExpGroups == 0 ) {
            /*
             * There aren't any expanded groups, so flPos must be a group and
             * its flPos will be the same as its group pos.  The
             * insert position is 0 (since the list is empty).
             */
			return PositionMetadata.obtain(pos.groupPos, pos.type, pos.groupPos, pos.childPos, null, 0);
		}

        /*
         * Binary search over the expanded groups to find either the exact
         * expanded group (if we're looking for a group) or the group that
         * contains the child we're looking for.
         */
		while( leftExpGroupIndex <= rightExpGroupIndex ) {
			midExpGroupIndex = ( rightExpGroupIndex - leftExpGroupIndex ) / 2 + leftExpGroupIndex;
			midExpGm = egml.get( midExpGroupIndex );

			if( pos.groupPos > midExpGm.gPos ) {
                /*
                 * It's after the current middle group, so search right
                 */
				leftExpGroupIndex = midExpGroupIndex + 1;
			}
			else if( pos.groupPos < midExpGm.gPos ) {
                /*
                 * It's before the current middle group, so search left
                 */
				rightExpGroupIndex = midExpGroupIndex - 1;
			}
			else if( pos.groupPos == midExpGm.gPos ) {
                /*
                 * It's this middle group, exact hit
                 */

				if( pos.type == ExpandableHListPosition.GROUP ) {
                    /* If it's a group, give them this matched group's flPos */
					return PositionMetadata.obtain(midExpGm.flPos, pos.type, pos.groupPos, pos.childPos, midExpGm, midExpGroupIndex);
				}
				else if( pos.type == ExpandableHListPosition.CHILD ) {
                    /* If it's a child, calculate the flat list pos */
					return PositionMetadata.obtain(midExpGm.flPos + pos.childPos + 1, pos.type, pos.groupPos, pos.childPos, midExpGm, midExpGroupIndex);
				}
				else {
					return null;
				}
			}
		}

        /*
         * If we've reached here, it means there was no match in the expanded
         * groups, so it must be a collapsed group that they're search for
         */
		if( pos.type != ExpandableHListPosition.GROUP ) {
            /* If it isn't a group, return null */
			return null;
		}

        /*
         * To figure out exact insertion and prior group positions, we need to
         * determine how we broke out of the binary search. We backtrack to see
         * this.
         */
		if( leftExpGroupIndex > midExpGroupIndex ) {

            /*
             * This would occur in the first conditional, so the flat list
             * insertion position is after the left group.
             *
             * The leftGroupPos is one more than it should be (from the binary
             * search loop) so we subtract 1 to get the actual left group.  Since
             * the insertion point is AFTER the left group, we keep this +1
             * value as the insertion point
             */
			final GroupMetadata leftExpGm = egml.get( leftExpGroupIndex - 1 );
			final int flPos = leftExpGm.lastChildFlPos + ( pos.groupPos - leftExpGm.gPos );

			return PositionMetadata.obtain(flPos, pos.type, pos.groupPos, pos.childPos, null, leftExpGroupIndex);
		}
		else if( rightExpGroupIndex < midExpGroupIndex ) {

            /*
             * This would occur in the second conditional, so the flat list
             * insertion position is before the right group. Also, the
             * rightGroupPos is one less than it should be (from binary search
             * loop), so we increment to it.
             */
			final GroupMetadata rightExpGm = egml.get( ++ rightExpGroupIndex );
			final int flPos = rightExpGm.flPos - ( rightExpGm.gPos - pos.groupPos );
			return PositionMetadata.obtain(flPos, pos.type, pos.groupPos, pos.childPos, null, rightExpGroupIndex);
		}
		else {
			return null;
		}
	}

	@Override
	public boolean areAllItemsEnabled() {
		return mExpandableListAdapter.areAllItemsEnabled();
	}

	@Override
	public boolean isEnabled( int flatListPos ) {
		final PositionMetadata metadata = getUnflattenedPos(flatListPos);
		final ExpandableHListPosition pos = metadata.position;

		boolean retValue;
		retValue = pos.type != ExpandableHListPosition.CHILD || mExpandableListAdapter.isChildSelectable(pos.groupPos, pos.childPos);
// Groups are always selectable

		metadata.recycle();

		return retValue;
	}

	public int getCount() {
        /*
         * Total count for the list view is the number groups plus the
         * number of children from currently expanded groups (a value we keep
         * cached in this class)
         */
		return mExpandableListAdapter.getGroupCount() + mTotalExpChildrenCount;
	}

	public Object getItem(int flatListPos ) {
		final PositionMetadata posMetadata = getUnflattenedPos(flatListPos);

		Object retValue;
		if( posMetadata.position.type == ExpandableHListPosition.GROUP ) {
			retValue = mExpandableListAdapter.getGroup(posMetadata.position.groupPos);
		}
		else if( posMetadata.position.type == ExpandableHListPosition.CHILD ) {
			retValue = mExpandableListAdapter.getChild(posMetadata.position.groupPos, posMetadata.position.childPos);
		}
		else {
			throw new RuntimeException( "Flat list position is of unknown type" );
		}

		posMetadata.recycle();

		return retValue;
	}

	public long getItemId( int flatListPos ) {
		final PositionMetadata posMetadata = getUnflattenedPos( flatListPos );
		final long groupId = mExpandableListAdapter.getGroupId(posMetadata.position.groupPos);

		long retValue;
		if( posMetadata.position.type == ExpandableHListPosition.GROUP ) {
			retValue = mExpandableListAdapter.getCombinedGroupId(groupId);
		}
		else if( posMetadata.position.type == ExpandableHListPosition.CHILD ) {
			final long childId = mExpandableListAdapter.getChildId( posMetadata.position.groupPos, posMetadata.position.childPos );
			retValue = mExpandableListAdapter.getCombinedChildId(groupId, childId);
		}
		else {
			throw new RuntimeException( "Flat list position is of unknown type" );
		}

		posMetadata.recycle();

		return retValue;
	}

	public View getView(int flatListPos, View convertView, ViewGroup parent ) {
		final PositionMetadata posMetadata = getUnflattenedPos( flatListPos );

		View retValue;
		if( posMetadata.position.type == ExpandableHListPosition.GROUP ) {
			retValue = mExpandableListAdapter.getGroupView( posMetadata.position.groupPos, posMetadata.isExpanded(), convertView, parent );
		}
		else if( posMetadata.position.type == ExpandableHListPosition.CHILD ) {
			final boolean isLastChild = posMetadata.groupMetadata.lastChildFlPos == flatListPos;

			retValue = mExpandableListAdapter.getChildView( posMetadata.position.groupPos, posMetadata.position.childPos, isLastChild, convertView, parent );
		}
		else {
			throw new RuntimeException( "Flat list position is of unknown type" );
		}

		posMetadata.recycle();

		return retValue;
	}

	@Override
	public int getItemViewType( int flatListPos ) {
		final PositionMetadata metadata = getUnflattenedPos( flatListPos );
		final ExpandableHListPosition pos = metadata.position;

		int retValue;
		if( mExpandableListAdapter instanceof HeterogeneousExpandableList) {
			HeterogeneousExpandableList adapter = (HeterogeneousExpandableList) mExpandableListAdapter;
			if( pos.type == ExpandableHListPosition.GROUP ) {
				retValue = adapter.getGroupType( pos.groupPos );
			}
			else {
				final int childType = adapter.getChildType( pos.groupPos, pos.childPos );
				retValue = adapter.getGroupTypeCount() + childType;
			}
		}
		else {
			if( pos.type == ExpandableHListPosition.GROUP ) {
				retValue = 0;
			}
			else {
				retValue = 1;
			}
		}

		metadata.recycle();

		return retValue;
	}

	@Override
	public int getViewTypeCount() {
		if( mExpandableListAdapter instanceof HeterogeneousExpandableList) {
			HeterogeneousExpandableList adapter = (HeterogeneousExpandableList) mExpandableListAdapter;
			return adapter.getGroupTypeCount() + adapter.getChildTypeCount();
		}
		else {
			return 2;
		}
	}

	@Override
	public boolean hasStableIds() {
		return mExpandableListAdapter.hasStableIds();
	}


	private void refreshExpGroupMetadataList(
			boolean forceChildrenCountRefresh, boolean syncGroupPositions ) {
		final ArrayList<GroupMetadata> egml = mExpGroupMetadataList;
		int egmlSize = egml.size();
		int curFlPos = 0;

        /* Update child count as we go through */
		mTotalExpChildrenCount = 0;

		if( syncGroupPositions ) {
			// We need to check whether any groups have moved positions
			boolean positionsChanged = false;

			for( int i = egmlSize - 1; i >= 0; i-- ) {
				GroupMetadata curGm = egml.get( i );
				int newGPos = findGroupPosition( curGm.gId, curGm.gPos );
				if( newGPos != curGm.gPos ) {
					if( newGPos == AdapterView.INVALID_POSITION ) {
						// Doh, just remove it from the list of expanded groups
						egml.remove( i );
						egmlSize--;
					}

					curGm.gPos = newGPos;
					if( ! positionsChanged ) positionsChanged = true;
				}
			}

			if( positionsChanged ) {
				// At least one group changed positions, so re-sort
				Collections.sort( egml );
			}
		}

		int gChildrenCount;
		int lastGPos = 0;
		for( int i = 0; i < egmlSize; i++ ) {
            /* Store in local variable since we'll access freq */
			GroupMetadata curGm = egml.get( i );

            /*
             * Get the number of children, try to refrain from calling
             * another class's method unless we have to (so do a subtraction)
             */
			if( ( curGm.lastChildFlPos == GroupMetadata.REFRESH ) || forceChildrenCountRefresh ) {
				gChildrenCount = mExpandableListAdapter.getChildrenCount( curGm.gPos );
			}
			else {
                /* Num children for this group is its last child's fl pos minus
                 * the group's fl pos
                 */
				gChildrenCount = curGm.lastChildFlPos - curGm.flPos;
			}

            /* Update */
			mTotalExpChildrenCount += gChildrenCount;

            /*
             * This skips the collapsed groups and increments the flat list
             * position (for subsequent exp groups) by accounting for the collapsed
             * groups
             */
			curFlPos += ( curGm.gPos - lastGPos );
			lastGPos = curGm.gPos;

            /* Update the flat list positions, and the current flat list pos */
			curGm.flPos = curFlPos;
			curFlPos += gChildrenCount;
			curGm.lastChildFlPos = curFlPos;
		}
	}

	/**
	 * Collapse a group in the grouped list view
	 *
	 * @param groupPos position of the group to collapse
	 */
	boolean collapseGroup( int groupPos ) {
		ExpandableHListPosition elGroupPos = ExpandableHListPosition.obtain( ExpandableHListPosition.GROUP, groupPos, - 1, - 1 );
		PositionMetadata pm = getFlattenedPos( elGroupPos );
		elGroupPos.recycle();
		if( pm == null ) return false;

		boolean retValue = collapseGroup( pm );
		pm.recycle();
		return retValue;
	}

	boolean collapseGroup( PositionMetadata posMetadata ) {
        /*
         * Collapsing requires removal from mExpGroupMetadataList
         */

        /*
         * If it is null, it must be already collapsed. This group metadata
         * object should have been set from the search that returned the
         * position metadata object.
         */
		if( posMetadata.groupMetadata == null ) return false;

		// Remove the group from the list of expanded groups
		mExpGroupMetadataList.remove(posMetadata.groupMetadata);

		// Refresh the metadata
		refreshExpGroupMetadataList( false, false );

		// Notify of change
		notifyDataSetChanged();

		// Give the callback
		mExpandableListAdapter.onGroupCollapsed(posMetadata.groupMetadata.gPos);

		return true;
	}



	boolean expandGroup( PositionMetadata posMetadata ) {
        /*
         * Expanding requires insertion into the mExpGroupMetadataList
         */

		if( posMetadata.position.groupPos < 0 ) {
			throw new RuntimeException( "Need group" );
		}

		/* The maximum number of allowable expanded groups. Defaults to 'no limit' */
		int mMaxExpGroupCount = Integer.MAX_VALUE;
		if( mMaxExpGroupCount == 0 ) return false;

		// Check to see if it's already expanded
		if( posMetadata.groupMetadata != null ) return false;

        /* Restrict number of expanded groups to mMaxExpGroupCount */
		if( mExpGroupMetadataList.size() >= mMaxExpGroupCount) {
            /* Collapse a group */
			GroupMetadata collapsedGm = mExpGroupMetadataList.get( 0 );

			int collapsedIndex = mExpGroupMetadataList.indexOf( collapsedGm );

			collapseGroup( collapsedGm.gPos );

            /* Decrement index if it is after the group we removed */
			if( posMetadata.groupInsertIndex > collapsedIndex ) {
				posMetadata.groupInsertIndex--;
			}
		}

		GroupMetadata expandedGm = GroupMetadata.obtain(GroupMetadata.REFRESH,
				GroupMetadata.REFRESH,
				posMetadata.position.groupPos,
				mExpandableListAdapter.getGroupId(posMetadata.position.groupPos));

		mExpGroupMetadataList.add( posMetadata.groupInsertIndex, expandedGm );

		// Refresh the metadata
		refreshExpGroupMetadataList( false, false );

		// Notify of change
		notifyDataSetChanged();

		// Give the callback
		mExpandableListAdapter.onGroupExpanded( expandedGm.gPos );

		return true;
	}

	public boolean isGroupExpanded( int groupPosition ) {
		GroupMetadata groupMetadata;
		for( int i = mExpGroupMetadataList.size() - 1; i >= 0; i-- ) {
			groupMetadata = mExpGroupMetadataList.get( i );

			if( groupMetadata.gPos == groupPosition ) {
				return true;
			}
		}

		return false;
	}

	ExpandableListAdapter getAdapter() {
		return mExpandableListAdapter;
	}

	public Filter getFilter() {
		ExpandableListAdapter adapter = getAdapter();
		if( adapter instanceof Filterable) {
			return ( (Filterable) adapter ).getFilter();
		}
		else {
			return null;
		}
	}

	ArrayList<GroupMetadata> getExpandedGroupMetadataList() {
		return mExpGroupMetadataList;
	}

	void setExpandedGroupMetadataList( ArrayList<GroupMetadata> expandedGroupMetadataList ) {

		if( ( expandedGroupMetadataList == null ) || ( mExpandableListAdapter == null ) ) {
			return;
		}

		// Make sure our current data set is big enough for the previously
		// expanded groups, if not, ignore this request
		int numGroups = mExpandableListAdapter.getGroupCount();
		for( int i = expandedGroupMetadataList.size() - 1; i >= 0; i-- ) {
			if( expandedGroupMetadataList.get( i ).gPos >= numGroups ) {
				// Doh, for some reason the client doesn't have some of the groups
				return;
			}
		}

		mExpGroupMetadataList = expandedGroupMetadataList;
		refreshExpGroupMetadataList( true, false );
	}

	@Override
	public boolean isEmpty() {
		ExpandableListAdapter adapter = getAdapter();
		return adapter == null || adapter.isEmpty();
	}


	int findGroupPosition( long groupIdToMatch, int seedGroupPosition ) {
		int count = mExpandableListAdapter.getGroupCount();

		if( count == 0 ) {
			return AdapterView.INVALID_POSITION;
		}

		// If there isn't a selection don't hunt for it
		if( groupIdToMatch == AdapterView.INVALID_ROW_ID ) {
			return AdapterView.INVALID_POSITION;
		}

		// Pin seed to reasonable values
		seedGroupPosition = Math.max( 0, seedGroupPosition );
		seedGroupPosition = Math.min( count - 1, seedGroupPosition );

		long endTime = SystemClock.uptimeMillis() + 100;

		long rowId;

		// first position scanned so far
		int first = seedGroupPosition;

		// last position scanned so far
		int last = seedGroupPosition;

		// True if we should move down on the next iteration
		boolean next = false;

		// True when we have looked at the first item in the data
		boolean hitFirst;

		// True when we have looked at the last item in the data
		boolean hitLast;

		// Get the item ID locally (instead of getItemIdAtPosition), so
		// we need the adapter
		ExpandableListAdapter adapter = getAdapter();
		if( adapter == null ) {
			return AdapterView.INVALID_POSITION;
		}

		while( SystemClock.uptimeMillis() <= endTime ) {
			rowId = adapter.getGroupId( seedGroupPosition );
			if( rowId == groupIdToMatch ) {
				// Found it!
				return seedGroupPosition;
			}

			hitLast = last == count - 1;
			hitFirst = first == 0;

			if( hitLast && hitFirst ) {
				// Looked at everything
				break;
			}

			if( hitFirst || ( next && ! hitLast ) ) {
				// Either we hit the top, or we are trying to move down
				last++;
				seedGroupPosition = last;
				// Try going up next time
				next = false;
			}
			else {
				// Either we hit the bottom, or we are trying to move up
				first--;
				seedGroupPosition = first;
				// Try going down next time
				next = true;
			}

		}

		return AdapterView.INVALID_POSITION;
	}

	protected class MyDataSetObserver extends DataSetObserver {
		@Override
		public void onChanged() {
			refreshExpGroupMetadataList( true, true );

			notifyDataSetChanged();
		}

		@Override
		public void onInvalidated() {
			refreshExpGroupMetadataList( true, true );

			notifyDataSetInvalidated();
		}
	}

	/**
	 * Metadata about an expanded group to help convert from a flat list
	 * position to either a) group position for groups, or b) child position for
	 * children
	 */
	static class GroupMetadata implements Parcelable, Comparable<GroupMetadata> {
		final static int REFRESH = - 1;

		/** This group's flat list position */
		int flPos;

        /* firstChildFlPos isn't needed since it's (flPos + 1) */

		/**
		 * This group's last child's flat list position, so basically
		 * the range of this group in the flat list
		 */
		int lastChildFlPos;

		/**
		 * This group's group position
		 */
		int gPos;

		/**
		 * This group's id
		 */
		long gId;

		private GroupMetadata() {
		}

		static GroupMetadata obtain( int flPos, int lastChildFlPos, int gPos, long gId ) {
			GroupMetadata gm = new GroupMetadata();
			gm.flPos = flPos;
			gm.lastChildFlPos = lastChildFlPos;
			gm.gPos = gPos;
			gm.gId = gId;
			return gm;
		}

		public int compareTo( @NonNull GroupMetadata another ) {

			return gPos - another.gPos;
		}

		public int describeContents() {
			return 0;
		}

		public void writeToParcel(Parcel dest, int flags ) {
			dest.writeInt( flPos );
			dest.writeInt( lastChildFlPos );
			dest.writeInt( gPos );
			dest.writeLong( gId );
		}

		public static final Creator<GroupMetadata> CREATOR = new Creator<GroupMetadata>() {

			public GroupMetadata createFromParcel( Parcel in ) {
				return GroupMetadata.obtain(in.readInt(), in.readInt(), in.readInt(), in.readLong());
			}

			public GroupMetadata[] newArray( int size ) {
				return new GroupMetadata[size];
			}
		};

	}

	/**
	 * Image type that contains an expandable list position (can refer to either a group
	 * or child) and some extra information regarding referred item (such as
	 * where to insert into the flat list, etc.)
	 */
	static public class PositionMetadata {

		private static final int MAX_POOL_SIZE = 5;
		private static final ArrayList<PositionMetadata> sPool = new ArrayList<>(MAX_POOL_SIZE);

		/** Image type to hold the position and its type (child/group) */
		public ExpandableHListPosition position;

		/**
		 * Link back to the expanded GroupMetadata for this group. Useful for
		 * removing the group from the list of expanded groups inside the
		 * connector when we collapse the group, and also as a check to see if
		 * the group was expanded or collapsed (this will be null if the group
		 * is collapsed since we don't keep that group's metadata)
		 */
		public GroupMetadata groupMetadata;

		/**
		 * For groups that are collapsed, we use this as the index (in
		 * mExpGroupMetadataList) to insert this group when we are expanding
		 * this group.
		 */
		public int groupInsertIndex;

		private void resetState() {
			if( position != null ) {
				position.recycle();
				position = null;
			}
			groupMetadata = null;
			groupInsertIndex = 0;
		}

		/**
		 * Use {@link #obtain(int, int, int, int, GroupMetadata, int)}
		 */
		private PositionMetadata() {
		}

		static PositionMetadata obtain(
				int flatListPos, int type, int groupPos, int childPos, GroupMetadata groupMetadata, int groupInsertIndex ) {
			PositionMetadata pm = getRecycledOrCreate();
			pm.position = ExpandableHListPosition.obtain( type, groupPos, childPos, flatListPos );
			pm.groupMetadata = groupMetadata;
			pm.groupInsertIndex = groupInsertIndex;
			return pm;
		}

		private static PositionMetadata getRecycledOrCreate() {
			PositionMetadata pm;
			synchronized( sPool ) {
				if( sPool.size() > 0 ) {
					pm = sPool.remove( 0 );
				}
				else {
					return new PositionMetadata();
				}
			}
			pm.resetState();
			return pm;
		}

		public void recycle() {
			resetState();
			synchronized( sPool ) {
				if( sPool.size() < MAX_POOL_SIZE ) {
					sPool.add( this );
				}
			}
		}

		/**
		 * Checks whether the group referred to in this object is expanded,
		 * or not (at the time this object was created)
		 *
		 * @return whether the group at groupPos is expanded or not
		 */
		public boolean isExpanded() {
			return groupMetadata != null;
		}
	}
}
