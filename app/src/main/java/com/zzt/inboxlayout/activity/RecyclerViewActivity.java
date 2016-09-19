package com.zzt.inboxlayout.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zzt.inbox.interfaces.OnDragStateChangeListener;
import com.zzt.inbox.widget.InboxBackgroundRecyclerView;
import com.zzt.inbox.widget.InboxLayoutBase;
import com.zzt.inbox.widget.InboxLayoutScrollView;
import com.zzt.inboxlayout.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Created by Xinyue Zhao
 */
public class RecyclerViewActivity extends AppCompatActivity {
	InboxLayoutScrollView inboxLayoutScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recyclerview_activity);

		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xdd000000));

		List<String> exposeItems = new ArrayList<>();
		for (int i = 0, cnt = 100;
				i < cnt;
				i++) {
			exposeItems.add(String.format("Expose %d", i));
		}

		final InboxBackgroundRecyclerView inboxBackgroundRecyclerView = (InboxBackgroundRecyclerView) findViewById(R.id.scroll);
		inboxBackgroundRecyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
		inboxBackgroundRecyclerView.setHasFixedSize(true);
		final ExposeListAdapter adapter = new ExposeListAdapter(exposeItems);
		inboxBackgroundRecyclerView.setAdapter(adapter);

		inboxLayoutScrollView = (InboxLayoutScrollView) findViewById(R.id.inboxlayout);
		inboxLayoutScrollView.setInboxBackground(inboxBackgroundRecyclerView);//绑定scrollview
		inboxLayoutScrollView.setCloseDistance(50);
		inboxLayoutScrollView.setOnDragStateChangeListener(new OnDragStateChangeListener() {
			@Override
			public void dragStateChange(InboxLayoutBase.DragState state) {
				switch (state) {
					case CANCLOSE:
						getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff5e5e5e));
						getSupportActionBar().setTitle("back");
						break;
					case CANNOTCLOSE:
						getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xdd000000));
						getSupportActionBar().setTitle("InboxLayout");
						break;
				}
			}
		});

		adapter.setInboxLayoutScrollView(inboxLayoutScrollView);
		adapter.setInboxBackgroundRecyclerView(inboxBackgroundRecyclerView);
	}

	private static final class ExposeListAdapter extends RecyclerView.Adapter<ExposeItemViewHolder> {

		private final List<String> mExposeItems;
		private InboxLayoutScrollView mInboxLayoutScrollView;
		private InboxBackgroundRecyclerView mInboxBackgroundRecyclerView;

		ExposeListAdapter(List<String> exposeItems) {
			mExposeItems = exposeItems;
		}

		void setInboxLayoutScrollView(InboxLayoutScrollView inboxLayoutScrollView) {
			mInboxLayoutScrollView = inboxLayoutScrollView;
		}

		void setInboxBackgroundRecyclerView(InboxBackgroundRecyclerView inboxBackgroundRecyclerView) {
			mInboxBackgroundRecyclerView = inboxBackgroundRecyclerView;
		}

		@Override
		public void onBindViewHolder(final ExposeItemViewHolder holder, int position) {
			holder.mTextView.setText(mExposeItems.get(position));
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mInboxBackgroundRecyclerView.setSelectedPosition(holder.getAdapterPosition());
					mInboxLayoutScrollView.openWithAnim(view);
				}
			});
		}

		@Override
		public ExposeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Context cxt = parent.getContext();
			LayoutInflater inflater = LayoutInflater.from(cxt);

			return new ExposeItemViewHolder(inflater.inflate(R.layout.item_recycler_view, parent, false));
		}

		@Override
		public int getItemCount() {
			return mExposeItems.size();
		}
	}

	private static final class ExposeItemViewHolder extends RecyclerView.ViewHolder {
		private TextView mTextView;

		ExposeItemViewHolder(View convertView) {
			super(convertView);
			mTextView = (TextView) convertView.findViewById(R.id.expose_item_tv);
		}
	}
}
