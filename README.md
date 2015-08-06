<title>圆盘显示控件  动画展示数据</title>
<br/>
<img src="https://github.com/siwangqishiq/DiscView/blob/master/screens/show.gif" width=300 />

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

xml布局:<br />
 《com.xinlan.discview.DiscView<br/>
        xmlns:app="http://schemas.android.com/apk/res-auto"<br/>
        app:dvStrokenWidth="10dp"                         <br/> <!--圆盘宽度-->  <br/>
        app:dvStrokenColor="#000000"                     <br/> <!--圆盘颜色-->   <br/>
        app:dvOuterCircleShow="true"                      <br/> <!--外装饰圆是否显示-->   <br/>
        app:dvOuterCirclePad="5dp"                         <br/> <!--外装饰圆与圆盘距离->   <br/>
        app:dvInnerCircleShow="true"                       <br/> <!--内装饰圆是否显示-->    <br/>
        app:dvInnerCirclePad="4dp"                          <br/> <!--内部装饰圆距离圆环边距-->    <br/>
        app:dvBottomCircleIsShow="true"                  <br/> <!--底部圆形是否显示-->    <br/>
        app:dvIndicatorDraw="@drawable/icon"         <br/> <!--圆形指示图片-->    <br/>
        app:dvStartRotateAngle="10"                         <br/> <!--圆盘开始旋转角度值-->    <br/>
        app:dvAngleRotateSpan="320"                       <br/> <!--圆盘可旋转角度范围-->     <br/>
        app:dvCircleMode="dot"                                 <br/> <!--圆盘模式                normal-普(pu)通(tong)模式   round-圆(wen)角(yi)模式   dot-虚(2)线(B)模式    -->  <br/>
        android:layout_width="fill_parent" <br/>
        android:layout_height="220dp" /><br/>
<br/>
  code:  <br />
        mDiscView.setValue(int value);//设置数值


