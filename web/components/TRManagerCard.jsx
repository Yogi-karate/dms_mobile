import React from "react";
// material-ui components
import withStyles from "@material-ui/core/styles/withStyles";
import Bookmarks from '@material-ui/icons/Bookmarks';
import Commute from '@material-ui/icons/Commute';
import Icon from "@material-ui/core/Icon";
import { makeStyles } from '@material-ui/core/styles';
import { red } from '@material-ui/core/colors';
import Badge from '@material-ui/core/Badge';

//Fontawesome icons

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faClipboardList, faCar, faWarehouse,faSortAmountUp,faBell } from '@fortawesome/free-solid-svg-icons'

// core components
import Card from "../components/components/Card/Card.jsx";
import CardBody from "../components/components/Card/CardBody.jsx";
import CardHeader from "../components/components/Card/CardHeader.jsx";
import { cardTitle,primaryCardHeader,successCardHeader } from "../components/assets/jss/material-kit-react.jsx";
import CustomLinearProgress from "../components/components/CustomLinearProgress/CustomLinearProgress.jsx";
import Danger from "../components/components/Typography/Danger.jsx";
import Info from "../components/components/Typography/Info.jsx";
import Primary from "../components/components/Typography/Primary.jsx";
import TRCustomDropDown from '../components/components/CustomDropdown/CustomDropdown';

//Cutom components from TurnRight
import TRChartDoughnut from '../components/TRChartDoughnut';
import TRChartPie from '../components/TRChartPie';



const style = {
  ... cardTitle,
  primaryCardHeader,
  textCenter: {
    textAlign: "center"
  },
  textLeft: {
      textAlign: "left"
  },
  textRight: {
      textAlign: "right"
  },
  margin: {
      margin: '1px'
  }
 };

 function renderFontAwesomeIcon(props) {
     
 }

class TRCard extends React.Component {
    
  render() {
    
    const { classes } = this.props;
    const cardIcon = this.props.cardIcon;
    const graphType = this.props.graphType;
    const cardIconInventory = this.props.cardIconInventory;
    const renderDropdown = this.props.renderDropdown;
    console.log("inside card. Printing....", this.props.graphData);
    const graphData = this.props.graphData;
    const graphLabel = this.props.graphLabel;
    const graphColors = this.props.graphColors;
    let cardIconRender;
    let graphRender;
    let dropdownComboRender;
    


    if(cardIcon === 'faCar') {
        cardIconRender = <FontAwesomeIcon icon={faCar} size='2x'/>

    }
    else if(cardIcon === 'faBell') {

        cardIconRender = <FontAwesomeIcon icon={faBell} size='2x'/>
    } else if(cardIcon === 'faClipboardList') {

        cardIconRender = <FontAwesomeIcon icon={faClipboardList} size='2x'/>
    } else if(cardIcon === 'faWarehouse') {

        cardIconRender = <FontAwesomeIcon icon={faWarehouse} size='2x'/>
    }

    if(graphType === 'doughnut') {
        graphRender = <TRChartDoughnut graphData={graphData} graphLabel = {graphLabel} graphColors = {graphColors}></TRChartDoughnut>
    }
    else if(graphType === 'pie') {
        graphRender = <TRChartPie graphData={graphData} graphLabel = {graphLabel} graphColors = {graphColors}></TRChartPie>
    } else if(graphType === 'doughnut') {
        graphRender = <TRChartDoughnut graphData={graphData} graphLabel = {graphLabel} graphColors = {graphColors}></TRChartDoughnut>

    } 

    if(renderDropdown)
    {

    }

    return (
        <Card style={{width: "19rem"}}>
            <CardHeader className={classes.textCenter} color={this.props.cardColor}>{cardIconRender}
                &nbsp;&nbsp;{this.props.cardTitle}
            </CardHeader>
            <div>
                    <CardBody>
                        {graphRender}
                    </CardBody>

            </div>
        </Card>
    );
  }
}

export default withStyles(style)(TRCard);