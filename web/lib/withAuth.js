import React from 'react';
import PropTypes from 'prop-types';
import Router from 'next/router';
import { connect } from 'react-redux';
import { isLoggedIn } from './store'

let globalUser = null;
const mapStateToProps = state => {
  console.log("state in withAuth", state);
  return { loggedIn: state.loggedIn};
}
const mapDispatchToProps = { isLoggedIn };
export default(
  Page,
  { loginRequired = true, logoutRequired = false, adminRequired = false } = {},
) => connect(mapStateToProps,
  mapDispatchToProps)(class BaseComponent extends React.Component {
    static propTypes = {
      user: PropTypes.shape({
        id: PropTypes.string,
        isAdmin: PropTypes.bool,
      }),
      isFromServer: PropTypes.bool.isRequired,
    };

    static defaultProps = {
      user: null,
    };
  //   async componentDidUpdate(prevProps) {
  //     console.log("the componentDidUpdate props are ", this.props.loggedIn, prevProps.loggedIn);
  //     if (this.props.loggedIn && this.props.loggedIn != prevProps.loggedIn) {
  //         console.log("changing state ----");
  //         Router.push('/public/login', '/login');
  //         return;
  //       }
  // }
    componentDidMount() {
      console.log("Mount of With Auth");
      const { user, isFromServer,loggedIn} = this.props;
      console.log("printing props in with Auth ",this.props);

      if (isFromServer) {
        globalUser = user;
      }

      if (loginRequired && !logoutRequired && (!user || !loggedIn)) {
        Router.push('/public/login', '/login');
        return;
      }

      if (logoutRequired && user) {
        Router.push('/');
      }

      if (adminRequired && (!user || !user.isAdmin)) {
        Router.push('/');
      }
    }

    static async getInitialProps(ctx) {
      const isFromServer = !!ctx.req;
      const user = ctx.req ? ctx.req.user && ctx.req.user.toObject() : globalUser;
      if (isFromServer && user) {
        user._id = user._id.toString();
      }

      const props = { user, isFromServer };

      if (Page.getInitialProps) {
        Object.assign(props, (await Page.getInitialProps(ctx)) || {});
      }

      return props;
    }

    render() {
      const { user,loggedIn } = this.props;
      console.log("The loggedin props in withAuth --- render",this.props.loggedIn);
      if (loginRequired && !logoutRequired && (!user || !loggedIn)) {
        Router.push('/public/login', '/login');
        return null;
      }     

      if (logoutRequired && user) {
        return null;
      }

      if (adminRequired && (!user || !user.isAdmin)) {
        return null;
      }
      console.log("Printing User in With Auth ----------- render",user);
      return <Page {...this.props} />;
    }
});


