package com.mao.lib_annotation_process
import com.google.auto.service.AutoService
import com.mao.lib_annotation.BindView
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement


/**
 * @author maoqitian
 * @Description: 注解处理器 处理 {@link com.mao.lib_annotation.BindView} 注解，自动生成对应的 Binding类
 * 生成类路径 build/generated/source/kapt/debug/com/mao/aptproject
 * @date 2021/4/08 0018 18:09
 */

@AutoService(Processor::class) // 自动生成META-INF/services 目录文件夹
class BindViewProcess : AbstractProcessor(){

    lateinit var filer: Filer
    //初始化调用
    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        filer = processingEnv.filer
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val linkedHashSet = LinkedHashSet<String>()
        linkedHashSet.add(BindView::class.java.canonicalName)
        return linkedHashSet
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    //注解处理 自动生成 binding View 绑定类
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {

        //遍历根元素
        roundEnv.rootElements.forEach { element ->
            //包名
            val packageStr = element.enclosingElement.toString()
            //类名
            val classStr = element.simpleName.toString()
            //重新构造类名
            val className = ClassName.get(packageStr, classStr + "Binding")
            //构造方法
            val constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(packageStr, classStr), "activity")

            //是否已经绑定
            var hasBinding = false

            element.enclosedElements.forEach { enclosedElement ->
                //只要声明的变量
                if (enclosedElement.kind == ElementKind.FIELD){
                    val bindView = enclosedElement.getAnnotation(BindView::class.java)
                    //有添加BindView注解
                    if(bindView!=null){
                        hasBinding = true
                        constructorBuilder.addStatement("activity.$"+"N = activity.findViewById($"+"L)",
                            enclosedElement.simpleName, bindView.value
                        )
                    }
                }
            }
            //最终创建出自动绑定的类
            val buildBindClass = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(constructorBuilder.build())
                .build()
            if (hasBinding){
                //如果是绑定 输出生成类
                JavaFile.builder(packageStr,buildBindClass).build().writeTo(filer)
            }
        }

        return false
    }
}