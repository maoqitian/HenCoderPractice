import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import com.mao.multitouchpointapplication.R

/**
 *  author : maoqitian
 *  date : 2021/3/7 22:45
 *  description : 扩展函数
 */

// dp 值转成 px 像素
val Float.dp
   get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)


// Int 值 dp 转 px
val Int.dp
get() = this.toFloat().dp


//根据指定的宽度加载 bitmap
fun getBitmap(resources: Resources,width : Int): Bitmap {
   val options = BitmapFactory.Options()
   //只读取尺寸
   options.inJustDecodeBounds = true
   BitmapFactory.decodeResource(resources,
      R.drawable.avatar,options)
   options.inJustDecodeBounds = false
   //获取原本尺寸比
   options.inDensity = options.outWidth
   //根据输入宽度加载
   options.inTargetDensity = width
   return BitmapFactory.decodeResource(resources,
      R.drawable.avatar,options)
}