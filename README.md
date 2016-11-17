There are 2 types of exceptions. One is triggered by DynamicQueryFactoryImpl and does not stop the proper functioning, i.e. I get results back.

13:08:53,511 ERROR [http-nio-9080-exec-2][DynamicQueryFactoryImpl:103] Unable find model com.liferay.journal.model.impl.JournalArticleImpl
java.lang.ClassNotFoundException: com.liferay.journal.model.impl.JournalArticleImpl

The other one is a Hibernate exception and is only in the case of a query and subquery.

13:08:53,513 ERROR [http-nio-9080-exec-2][BasePersistenceImpl:260] Caught unexpected exception org.hibernate.MappingException
13:08:53,516 ERROR [http-nio-9080-exec-2][TestPortlet:51] null
com.liferay.portal.kernel.exception.SystemException: org.hibernate.MappingException: Unknown entity: com.liferay.journal.model.JournalArticle

It seems the issue is independant of the use of a jar, I also get the error if I use a Dynamicquery and subquery in the portlet plugin.
