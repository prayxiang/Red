package com.prayxiang.red.service;

public class IndicateHelper implements IGrabHelper{

	private boolean mCanRetrieve = true;
	private boolean mRunning;
	
	private boolean mIsNotifyEnter;
	public boolean isNotifyEnter() {
		return mIsNotifyEnter;
	}

	public void setNotifyEnter(boolean mIsNotifyEnter) {
		this.mIsNotifyEnter = mIsNotifyEnter;
	}

	@Override
	public boolean isCanRetrieve() {
		
		return mCanRetrieve;
	}

	@Override
	public void setCanRetrieve(boolean b) {
		// TODO Auto-generated method stub
		mCanRetrieve = b;
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return mRunning;
	}

	@Override
	public void setRunning(boolean b) {
		mRunning = b;
	}

}
