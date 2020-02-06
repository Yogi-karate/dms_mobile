import React from 'react';
import PropTypes from 'prop-types';
import Router from 'next/router';
import { connect } from 'react-redux';
import { userTeam } from './store'
import { getTeams } from './api/admin';

let globalUser = null;
const mapStateToProps = state => {
  console.log("state in withAuth", state);
  return { loggedIn: state.loggedIn };
}
const mapDispatchToProps = { userTeam };
export default (
  Page,
  { loginRequired = true, logoutRequired = false, adminRequired = false } = {},
) => connect(null,
  mapDispatchToProps)(class BaseComponent extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        loggedIn: false,
      };
    }
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
    async componentDidMount() {
      console.log("Mount of With Auth");
      const { user, isFromServer } = this.props;
      let loggedIn = this.state.loggedIn;
      console.log("printing props in with Auth ", this.props);

      if (isFromServer) {
        globalUser = user;
      }
      const teams = await this.getRoles();
      if (teams) {
        console.log("setting logged in to true", teams);
        loggedIn = true;
        this.setState({ loggedIn: true });
        this.props.userTeam(teams);
      }
      if (loginRequired && !logoutRequired && (!user || !loggedIn)) {
        Router.push('/');
        return;
      }
      if (logoutRequired && user) {
        Router.push('/dashboard');
      }
      if (adminRequired && (!user || !user.isAdmin)) {
        Router.push('/');
      }
    }
    async getRoles() {
      console.log("Inside get roles ");
      try {
        const data = await getTeams();
        console.log("The result is - ", data);
        console.log("The result teams is ", data.result.teams.records);
        if (data == null || data == [] || data.result.teams.records == []) {
          return [];
        } else {
          let result = data.result.teams.records.map(team => {
            return [team.name, team.id];
          })
          console.log("The resultttttttt teams is ", result);
          return result;
        }
      } catch (err) {
        console.log(err); // eslint-disable-line
        return null;
      }
    }
    static async getInitialProps(ctx) {
      console.log("Get intial props being called---------");
      const isFromServer = !!ctx.req;
      const user = ctx.req ? ctx.req.user && ctx.req.user : globalUser;
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
      const { user } = this.props;
      const  loggedIn = this.state.loggedIn;
      console.log("The loggedin props in withAuth --- render", loggedIn);
      console.log("The loggedin props in withAuth --- render", user);
      if (loginRequired && !logoutRequired && (!user || !loggedIn)) {
        return null;
      }

      if (logoutRequired && user) {
        return null;
      }

      if (adminRequired && (!user || !user.isAdmin)) {
        return null;
      }
      console.log("Printing User in With Auth ----------- render", user);
      return <Page {...this.props} />;
    }
  });


