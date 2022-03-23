package com.skyqgo.recruitmenttest.presentation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AutoFitRecyclerView extends RecyclerView
{
	private GridLayoutManager manager;
	private int columnWidth = -1;

	public AutoFitRecyclerView(Context context)
	{
		super(context);
		initialization(context, null);
	}

	public AutoFitRecyclerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialization(context, attrs);
	}

	public AutoFitRecyclerView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		initialization(context, attrs);
	}

	private void initialization(Context context, AttributeSet attrs)
	{
		try
		{
			if (attrs != null)
			{
				// list the attributes we want to fetch
				int[] attrsArray = {
					android.R.attr.columnWidth
				};
				TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
				//retrieve the value of the 0 index, which is columnWidth
				columnWidth = array.getDimensionPixelSize(0, -1);
				array.recycle();
			}
			manager = new GridLayoutManager(context, 1);
			setLayoutManager(manager);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthSpec, int heightSpec)
	{
		super.onMeasure(widthSpec, heightSpec);
		try
		{
			if (columnWidth > 0)
			{
				//The spanCount will always be at least 1
				int spanCount = Math.max(1, getMeasuredWidth() / columnWidth);
				manager.setSpanCount(spanCount);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
