import React, {Component} from "react";
// material-ui components
import withStyles from "@material-ui/core/styles/withStyles";
import assessment from "@material-ui/icons/Assessment"
import Bookmarks from '@material-ui/icons/Bookmarks';
// core components
import Card from "./components/Card/Card.jsx";
import CardBody from "./components/Card/CardBody.jsx";
import Button from "./components/CustomButtons/Button.jsx";

import imagesStyles from "./assets/jss/material-kit-react/imagesStyles.jsx";

import { cardTitle, primaryColor } from "./assets/jss/material-kit-react.jsx";

//import AppleImage from "./static/apple-icon.png"
import Icon from "@material-ui/core/Icon"

const styles = {
  ... cardTitle, 
      primaryColor,
  
};

class TRCard1 extends Component {

  render() {

    const { classes } = this.props;

    return (
      <Card style={{width: "20rem"}}>
        
        <CardBody>
          <h4 className={classes.cardTitle}>
          {/* <Bookmarks></Bookmarks> */}
          Total Enquiries</h4>
          <img src="../static/icons8-sales-performance-32.png" alt="Logo" />
          <b>145 Enquiries</b>
          <Button variant="outlined" color="primary" >
            <Icon>location_on</Icon>
            Find me
          </Button>
        </CardBody>
      </Card>
    );
  };
}
export default withStyles(styles)(TRCard1);