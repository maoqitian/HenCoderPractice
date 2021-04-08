package com.mao.lib_annotation_process
import com.google.auto.service.AutoService
import com.mao.lib_annotation.BindView
import java.util.LinkedHashSet
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement


/**
 * @author maoqitian
 * @Description: 注解处理器 处理 {@link com.mao.lib_annotation.BindView} 注解，自动生成对应的 Binding类
 * @date 2021/4/08 0018 18:09
 */

@AutoService(Processor::class) // 自动生成META-INF/services 目录文件夹
class BindViewProcess :AbstractProcessor(){

    //初始化调用
    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val linkedHashSet = LinkedHashSet<String>()
        linkedHashSet.add(BindView::class.java.canonicalName)
        return super.getSupportedAnnotationTypes()
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    //注解处理 自动生成 binding View 绑定类
    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        return false
    }
}