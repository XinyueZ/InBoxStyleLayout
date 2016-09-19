# InboxLayout
Like Google Inbox effect to open-close list and detail by dragging up/down.

This version supports RecyclerView as "background" which shows list of items.

The InBoxLayoutXXXX can still be used for showing detail.

# ScreenShot

 
 ![image](/screenshot/recycler-sample.gif)   ![image](/screenshot/pic.gif)  ![image](/screenshot/pic1.gif) 
 


# Usage
###step 1 
 
```xml
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.zzt.inbox.widget.InboxBackgroundRecyclerView
            android:id="@+id/scroll"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:overScrollMode="always"
            android:scrollbars="none">
        </com.zzt.inbox.widget.InboxBackgroundRecyclerView>

        <com.zzt.inbox.widget.InboxLayoutScrollView
            android:id="@+id/inboxlayout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:visibility="invisible">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:text="@string/filler_text"/>
        </com.zzt.inbox.widget.InboxLayoutScrollView>

    </FrameLayout> 
```

###step 2

```java
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
 						//... 
 						break;
 					case CANNOTCLOSE:
 											//... 
 						break;
 				}
 			}
 		});
 
 		adapter.setInboxLayoutScrollView(inboxLayoutScrollView);
 		adapter.setInboxBackgroundRecyclerView(inboxBackgroundRecyclerView);
```  
    
###step 3
 
```java
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
```  

##### Thanks

For good start thx [zhaozhentao](https://raw.githubusercontent.com/zhaozhentao/)


###  License

The MIT License (MIT)

Copyright (c) 2016 Xinyue Chris Zhao, 2015 zhaozhentao

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


