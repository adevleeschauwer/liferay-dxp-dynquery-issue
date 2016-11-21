package be.gfi.dxp.portlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.impl.JournalArticleModelImpl;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

public class TestPortlet extends AbstractContentPortlet {
	private static final Log _log = LogFactoryUtil.getLog(TestPortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		_log.info("Performing count");
		try {
			renderRequest.setAttribute("count", count());
		} catch (Exception e) {
			_log.error(e);
		}

		_log.info("Performing articles");
		try {
			renderRequest.setAttribute("articles", articles());
		} catch (Exception e) {
			_log.error(e);
		}

		_log.info("Performing articlesLastVersion");
		try {
			renderRequest.setAttribute("articlesLastVersion", articlesLastVersion());
		} catch (Exception e) {
			_log.error(e);
		}

		_log.info("Performing articlesLastVersionNoJar");
		try {
			renderRequest.setAttribute("articlesLastVersionNoJar", articlesLastVersionNoJar());
		} catch (Exception e) {
			_log.error(e);
		}

		super.doView(renderRequest, renderResponse);
	}

	protected List<Object> articlesLastVersionNoJar() {

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

}
