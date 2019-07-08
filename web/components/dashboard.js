import React from "react";
import PropTypes from "prop-types";
// react plugin for creating charts
import ChartistGraph from "react-chartist";
// @material-ui/core
import withStyles from "@material-ui/core/styles/withStyles";
import Icon from "@material-ui/core/Icon";
// @material-ui/icons
import Store from "@material-ui/icons/Store";
import Warning from "@material-ui/icons/Warning";
import DateRange from "@material-ui/icons/DateRange";
import LocalOffer from "@material-ui/icons/LocalOffer";
import Update from "@material-ui/icons/Update";
import ArrowUpward from "@material-ui/icons/ArrowUpward";
import AccessTime from "@material-ui/icons/AccessTime";
import Accessibility from "@material-ui/icons/Accessibility";
import BugReport from "@material-ui/icons/BugReport";

// core components
import GridItem from "../dashboard_components/Grid/GridItem.js";
import GridContainer from "../dashboard_components/Grid/GridContainer.js";
//import Danger from "components/Typography/Danger.jsx";
import Card from "../dashboard_components/Card/Card.js";
import CardHeader from "../dashboard_components/Card/CardHeader.js";
import CardIcon from "../dashboard_components/Card/CardIcon.js";
import CardFooter from "../dashboard_components/Card/CardFooter.js";
import CardBody from "../dashboard_components/Card/CardBody.js";

// import { bugs, website, server } from "variables/general.jsx";
import Chartist from "chartist";
import {getDashboard} from "../lib/api/admin.js"
import dashboardStyle from "../lib/dashboard_styles/dashboardStyle.jsx";

class Dashboard extends React.Component {
  state = {
    value: 0,
    stages:{}
  };
  handleChange = (event, value) => {
    this.setState({ value });
  };

  handleChangeIndex = index => {
    this.setState({ value: index });
  };
  async componentDidMount() {
    try {
      console.log("the dashboard props",this.props);
      const data = await getDashboard(this.props.user);
      console.log("the dashboard data",data);
      let stages = {}
      data.forEach(stage => {
      console.log(stage.state);
      stages[stage.state] = stage.result[0].stage_id_count;
      });
      this.setState({stages:stages});
      console.log("dashboard state",this.state);
      console.log("the overdue stage",this.state.stages.overdue);
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
  }
  render() {
    const { classes } = this.props;
    const {user} = this.props
    console.log("The stages are ",this.state.stages["overdue"]);
    console.log("The user in dashboard",this.props);
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={6} md={3}>
            <Card>
              <CardHeader color="warning" stats icon>
                <CardIcon color="warning">
                  <Icon>content_copy</Icon>
                </CardIcon>
                <p className={classes.cardCategory}>Today</p>
                <h3 className={classes.cardTitle}>
                { this.state.stages.today}
                </h3>
              </CardHeader>
              <CardFooter stats>
                <div className={classes.stats}>
                  {/* <Danger>
                    <Warning />
                  </Danger> */}
                  <a href="#pablo" onClick={e => e.preventDefault()}>
                    Get more space
                  </a>
                </div>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={6} md={3}>
            <Card>
              <CardHeader color="success" stats icon>
                <CardIcon color="success">
                  <Store />
                </CardIcon>
                <p className={classes.cardCategory}>Planned</p>
                <h3 className={classes.cardTitle}> { this.state.stages.planned}</h3>
              </CardHeader>
              <CardFooter stats>
                <div className={classes.stats}>
                  <DateRange />
                  Last 24 Hours
                </div>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={6} md={3}>
            <Card>
              <CardHeader color="danger" stats icon>
                <CardIcon color="danger">
                  <Icon>info_outline</Icon>
                </CardIcon>
                <p className={classes.cardCategory}>Overdue</p>
                <h3 className={classes.cardTitle}> { this.state.stages.overdue}</h3>
              </CardHeader>
              <CardFooter stats>
                <div className={classes.stats}>
                  <LocalOffer />
                  Tracked from Github
                </div>
              </CardFooter>
            </Card>
          </GridItem>
          <GridItem xs={12} sm={6} md={3}>
            <Card>
              <CardHeader color="info" stats icon>
                <CardIcon color="info">
                  <Accessibility />
                </CardIcon>
                <p className={classes.cardCategory}>Booked</p>
                <h3 className={classes.cardTitle}>+245</h3>
              </CardHeader>
              <CardFooter stats>
                <div className={classes.stats}>
                  <Update />
                  Just Updated
                </div>
              </CardFooter>
            </Card>
          </GridItem>
        </GridContainer>
      </div>
    );
  }
}

Dashboard.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(dashboardStyle)(Dashboard);
