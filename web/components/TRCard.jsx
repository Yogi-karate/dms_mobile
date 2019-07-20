import React, { Component } from "react";
//import "tabler-react/dist/Tabler.css";
import { Card, Button, AccountDropdown,Icon } from "tabler-react";
//import ProgressBar from 'react-bootstrap/ProgressBar';
//import inventoryIcon from '../static/icons8-traffic-jam-48.png'
import indigo from '@material-ui/core/colors/indigo';
import pink from '@material-ui/core/colors/pink';
import red from '@material-ui/core/colors/red';

const now = 60;

class TRCard extends Component {

    
    render() {
        return (
                
      <div class="col col-sm-6 col-lg-3">
          <Card >
              <Card.Header >
              <Card.Title className="p-3 text-center alert-link">{this.props.cardTitle}</Card.Title>
              </Card.Header>
              <Card.Body className="p-3 text-center">
                  <div className="d-flex align-items-center">
                      <span className={this.props.iconBgClass}>
                    <img src={this.props.iconURL} alt="Logo" />
                         {/* <Icon prefix={this.props.iconPrefix} name={this.props.iconName} /> */}
                      </span>
                          <div>
                              <div className="h4 m-0">
                                  <a href="#">{this.props.count} <small></small></a>
                              </div>
                              <div>
                                <small className={this.props.subTextColor}>{this.props.subCount} </small>
                                {/* <ProgressBar now="60" label="60" /> */}
                              </div>
                                  
                          </div>
                  </div>
              </Card.Body>
          </Card>
      </div>
      
  
      );
    }
  }
  
  export default TRCard;