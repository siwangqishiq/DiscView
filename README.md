<title>圆盘显示控件  动画展示数据</title>


<br/>
<img src="https://github.com/siwangqishiq/DiscView/blob/master/screens/1.png" width=300 />
<br/>
<img src="https://github.com/siwangqishiq/DiscView/blob/master/screens/2.png" width=300 />
<br/>
<img src="https://github.com/siwangqishiq/DiscView/blob/master/screens/3.png" width=300 />
<br/>
<img src="https://github.com/siwangqishiq/DiscView/blob/master/screens/4.png" width=300 />
<br/>

使用方式:

xml布局
 <com.xinlan.discview.DiscView
        app:dvStrokenWidth="10dp"                          <!--圆盘宽度-->
        app:dvStrokenColor="#000000"                      <!--圆盘颜色-->
        app:dvOuterCircleShow="true"                       <!--外装饰圆是否显示-->
        app:dvOuterCirclePad="5dp"                          <!--外装饰圆与圆盘距离->
        app:dvInnerCircleShow="true"                        <!--内装饰圆是否显示-->
        app:dvInnerCirclePad="4dp"                           <!--内部装饰圆距离圆环边距-->
        app:dvBottomCircleIsShow="true"                   <!--底部圆形是否显示-->
        app:dvIndicatorDraw="@drawable/icon"         <!--圆形指示图片-->
        app:dvStartRotateAngle="10"                          <!--圆盘开始旋转角度值-->
        app:dvAngleRotateSpan="320"                        <!--圆盘可旋转角度范围-->
        app:dvCircleMode="dot"                                  <!--圆盘模式                normal-普(pu)通(tong)模式   round-圆(wen)角(yi)模式   dot-虚(2)线(B)模式    -->
        android:layout_width="fill_parent"
        android:layout_height="220dp" />

  code:
        mDiscView.setValue(int value);//设置数值


