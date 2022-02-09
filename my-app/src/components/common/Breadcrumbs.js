import {Link, useLocation } from 'react-router-dom';
import { Breadcrumb } from 'antd';

const Breadcrumbs = () => {
    const breadcrumbNameMap = {
        '/apps': 'Application List',
        '/apps/1': 'Application1',
        '/apps/2': 'Application2',
        '/apps/1/detail': 'Detail',
        '/apps/2/detail': 'Detail',
      };

    const location = useLocation();
    const pathSnippets = location.pathname.split('/').filter(i => i);
    console.log("path snippers", pathSnippets);
    const extraBreadcrumbItems = pathSnippets.map((_, index) => {
      const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
      console.log("url", url);
      console.log("breadcrumbNameMap[url]", breadcrumbNameMap[url]);
      return (
        <Breadcrumb.Item key={url}>
          <Link to={url}>{breadcrumbNameMap[url]}</Link>
        </Breadcrumb.Item>
      );
    });

    const breadcrumbItems = [
      <Breadcrumb.Item key="home">
        <Link to="/">Головна сторінка</Link>
      </Breadcrumb.Item>,
    ].concat(extraBreadcrumbItems);

    return (
        <>
            <Breadcrumb>{breadcrumbItems}</Breadcrumb>
        </>
    )
}
export default Breadcrumbs;