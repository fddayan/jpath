# Generated by Buildr 1.4.4, change to your liking
# Version number for this release
VERSION_NUMBER = "1.0.0"
# Group identifier for your projects
GROUP = "jpath"
COPYRIGHT = ""

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://www.ibiblio.org/maven2/"

desc "The Jpath project"
define "jpath" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  compile.with 'org.apache.commons:commons-jexl:jar:2.0.1','commons-collections:commons-collections:jar:3.2.1','commons-lang:commons-lang:jar:2.3','commons-logging:commons-logging:jar:1.1.1'
  test.using :testng
end