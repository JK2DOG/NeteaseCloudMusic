package com.imooc.imooc_voice;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.imooc.imooc_voice.view.login.SplashDelegate;
import com.imooc.lib_audio.app.AudioHelper;
import com.imooc.lib_common_ui.app.Netease;
import com.imooc.lib_common_ui.base.ProxyActivity;
import com.imooc.lib_common_ui.delegate.NeteaseDelegate;
import com.imooc.lib_common_ui.utils.SharePreferenceUtil;
import com.imooc.lib_common_ui.utils.StatusBarUtil;

import pub.devrel.easypermissions.EasyPermissions;


public class ImoocActivity extends ProxyActivity{


	@Override
	public NeteaseDelegate setRootDelegate() {
		return new SplashDelegate();
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//取消ActionBar
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.hide();
		}
		Netease.getConfigurator().withActivity(this);
		StatusBarUtil.statusBarLightMode(this);

		String[] perms = {Manifest.permission.READ_PHONE_STATE
				, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
				, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

		if (!EasyPermissions.hasPermissions(this, perms)) {
			EasyPermissions.requestPermissions(this, "请打开相关权限", 1, perms);
		}

		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//保存当前播放的歌曲 进度
		SharePreferenceUtil.getInstance(this).saveLatestSong(AudioHelper.getNowPlaying());
		SharePreferenceUtil.getInstance(this).saveMusicList(AudioHelper.getCurrentQueue());
	}

	@Override
	public void post(Runnable runnable) {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		getSupportDelegate().pop();
	}
}
