package be.gfi.dxp.portlet;

import java.util.List;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

/**
 * Base class for portlets
 */
public abstract class AbstractContentPortlet extends MVCPortlet {

	protected List<Object> articles() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "article",
				PortalClassLoaderUtil.getClassLoader());

		return JournalArticleLocalServiceUtil.dynamicQuery(query, 0, 10);
	}

	protected Long count() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "article",
				PortalClassLoaderUtil.getClassLoader());

		query.setProjection(ProjectionFactoryUtil.countDistinct("articleId"));

		return (Long) JournalArticleLocalServiceUtil.dynamicQuery(query, QueryUtil.ALL_POS, QueryUtil.ALL_POS).get(0);
	}

	protected List<Object> articlesLastVersion() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "articleParent",
				PortalClassLoaderUtil.getClassLoader());

		// subquery retrieving the max(version)
		final DynamicQuery subquery = DynamicQueryFactoryUtil
				.forClass(JournalArticle.class, "subQuery", PortalClassLoaderUtil.getClassLoader())
				.add(PropertyFactoryUtil.forName("articleParent.articleId").eqProperty("articleId"))
				.setProjection(ProjectionFactoryUtil.max("version"));

		// add subquery
		query.add(PropertyFactoryUtil.forName("version").eq(subquery));

		return JournalArticleLocalServiceUtil.dynamicQuery(query, 0, 10);
	}

}
