# AutoUpdata

### 这是一个基于OkHttp3.8的android自动升级的库；

## 简介

- 基于OkHttp3.8用于app自更新的一个库；


##  特性

-	配置方式灵活人性化：可以使用application配置+ activity调用的方式；也可以使用Activity配置+调用的方式；

- 甚至可以不配置除url以外的任何参数；

- 调用方式简单方便，基于建造者模式的链式调用，使用起来方便而灵活；

- 适配android7.0;


## 使用方式：

#### 1.在project的build.gradle中添加以下代码：

```
allprojects {
		repositories {
			...
			//这一行
			maven { url 'https://jitpack.io' }
		}
	}
```

#### 2.在项目module的build.gradle中添加以下代码：

```
dependencies {
	//这一行	
	implementation 'com.github.crykid:AutoUpdata:v1.0.0'
}
```

#### 3.1.配置参数解释：

```
/**
 * Created by : mr.lu
 * Created at : 2018/12/1 at 15:49
 * Description: 自动升级配置抽象接口
 */
public interface IAutoUpdataerConfig {

    /**
     * apk下载的url
     * 一般都应该是get方式的，所以我没有做请求方式的配置
     *
     * @param url 下载apk的url
     * @return
     */
    IAutoUpdataerConfig url(String url);

    /**
     * 设置apk存储文件夹的名字及provider的Authorities，
     * 可以不主动调用，因为我添加了默认配置在Constants{@link com.crykid.updata.Constants}
     *
     * @param desDir      将要存储apk的文件夹名字，不用带系统目录，eg: auto-updata,example-dir ...
     * @param authorities 文件夹的fileProvider的authorities，就是manifest里面的authorities（不包含applicationId）
     *                    eg：name.provider,example.provider,test.provider
     * @return
     */
    IAutoUpdataerConfig desDir(String desDir, String authorities);

    /**
     * 设置apk文件名字
     *
     * @param apkName apk的文件名字，带或不带".apk"后缀都可以
     * @return
     */
    IAutoUpdataerConfig apkName(String apkName);

    /**
     * 是否强制升级
     *
     * @param force type boolean：true-强制升级（不可取消）
     * @return
     */
    IAutoUpdataerConfig forceUpdata(boolean force);

}

```

> 以上是目前为止参数配置的全部方法，后面你会发现AutoUpdataer类和AutoUpdataerConfig有相同的方法，这是因为他们同时实现了这个接口，具体功能不做过多解释，代码注释的很清楚；

#### 3.2使用方式一：

- Step1，在在Application中：

```
 AutoUpdataerConfig
                .getInstance()
                .url("https://www.test.com/aa.apk")
                .desDir("auto-updata-apk", "example.provider")
                .apkName("auto-updata")
                .forceUpdata(true)
                .configure();

```

> *注：以上参数中，除了url，其它配置方法都是可选的，我提供了默认的方式；

- Step2,在需要升级的地方，例如SplashActivity，或HomeActivity中：

```
 		//不带回调的方式
        AutoUpdataer.getInstance().dialog(this);
        
        //带回调的方式
        AutoUpdataer.getInstance().dialog(this, new IAutoUpdataerCallback() {
            @Override
            public void uploadFailed() {
				//doSth
            }

            @Override
            public void uploadSuccess() {
				//doSth
            }
        });

```

#### 3.3.使用方式二：

- 相比方式一，少了application配置的方式：

```

        //使用方式二，不需要application中配置，全部在使用的时候再配置
        AutoUpdataer
                .getInstance()
                //下载地址,需要完整的地址
                .url("https://www.test.com")
                //目标存储目录/文件夹 和authorities,需要您自己在manifest和xml中做相关配置
                .desDir("demo-dir", "example.provider")
                //apk文件名，可以不包含后缀
                .apkName("test.apk")
                //是否强制升级
                .forceUpdata(true)
                //通过dialog提示进度的方式升级
                .dialog(this, new IAutoUpdataerCallback() {
                    @Override
                    public void uploadFailed() {
                        Log.d(TAG, "uploadFailed: ");
                    }

                    @Override
                    public void uploadSuccess() {
                        Log.d(TAG, "uploadSuccess: ");
                    }
                });

```

> 注：同方式一，这里除了url是必须的，其它配置方法都是可选的，我提供了默认的方式；

## 效果

![效果图](https://raw.githubusercontent.com/crykid/AutoUpdata/develop/pics/running.jpg)


## 补充

- 当前仅仅有dialog更新的方式，还需要Notification更新方式，后续我很快会更新；

- 现在进度弹窗暂时不可以控制文案，后续我会很快更新相关接口；


