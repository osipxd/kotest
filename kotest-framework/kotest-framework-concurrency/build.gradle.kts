plugins {
   id("java")
   id("kotlin-multiplatform")
   id("java-library")
   id("com.adarshr.test-logger")
}

kotlin {

   targets {
      jvm {
         compilations.all {
            kotlinOptions {
               jvmTarget = "1.8"
            }
         }
      }
      js(IR) {
         browser()
         nodejs()
      }
      linuxX64()

      mingwX64()

      macosX64()
      macosArm64()

      tvos()
      tvosSimulatorArm64()

      watchosArm32()
      watchosArm64()
      watchosX86()
      watchosX64()
      watchosSimulatorArm64()

      iosX64()
      iosArm64()
      iosArm32()
      iosSimulatorArm64()
   }

   sourceSets {

      val commonMain by getting {
         dependencies {
            compileOnly(kotlin("stdlib"))
            implementation(Libs.Coroutines.coreCommon)
            implementation(project(Projects.Common))
            implementation(project(Projects.Framework.api))
         }
      }

      val jvmTest by getting {
         dependencies {
            implementation(project(Projects.Assertions.Core))
            implementation(project(Projects.Framework.engine))
            implementation(project(Projects.JunitRunner))
         }
      }

      all {
         languageSettings.optIn("kotlin.time.ExperimentalTime")
         languageSettings.optIn("kotlin.experimental.ExperimentalTypeInference")
      }
   }
}

apply(from = "../../publish-mpp.gradle.kts")
