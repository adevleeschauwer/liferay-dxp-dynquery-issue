package be.gfi.dxp.portlet;

import java.util.List;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.impl.JournalArticleModelImpl;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * Base class for portlets
 */
public abstract class AbstractContentPortlet extends MVCPortlet {

	protected List<Object> articles() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "article", getClassLoader());

		return JournalArticleLocalServiceUtil.dynamicQuery(query, 0, 10);
	}

	protected Long count() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "article", getClassLoader());

		query.setProjection(ProjectionFactoryUtil.countDistinct("articleId"));

		return (Long) JournalArticleLocalServiceUtil.dynamicQuery(query, QueryUtil.ALL_POS, QueryUtil.ALL_POS).get(0);
	}

	protected List<Object> articlesLastVersion() {
		// Very basic query
		// Is this the right classloader to use for legacy portlets ?
		final DynamicQuery query = DynamicQueryFactoryUtil.forClass(JournalArticle.class, "articleParent", getClassLoader());

		// subquery retrieving the max(version)
		final DynamicQuery subquery = DynamicQueryFactoryUtil
				.forClass(JournalArticle.class, "subQuery", getClassLoader())
				.add(PropertyFactoryUtil.forName("articleParent.articleId").eqProperty("articleId"))
				.setProjection(ProjectionFactoryUtil.max("version"));

		// add subquery
		query.add(PropertyFactoryUtil.forName("version").eq(subquery));

		return JournalArticleLocalServiceUtil.dynamicQuery(query, 0, 10);
	}

	// This is the right classloader to use, and you can get this classloader.

	// However, note that the Impl models are wrongly exported, and, if you want to use
	// a Dynamic Query where the model implementation is not exported, you will need to reference
	// some of the classes of the module containing the impl (note that you don't really know it,
	// but you could guess it, because in Liferay we always define this classes in the *-service modules)

	// Sorry, but I need to highlight again that this is a really bad idea, where we are exposing all the internals
	// of a module, and coupling all the potential consumers (and not only coupling, but doing all kind of magic and
	// fragile things.

	protected ClassLoader getClassLoader() {
		return JournalArticleModelImpl.class.getClassLoader();
	}

}
