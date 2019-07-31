import React, { Component } from 'react';

import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import { getEnqStateData } from '../../lib/api/dashboard';
import { connect } from 'react-redux';

const options = {
  filter: true,
  search: true,
  print: false,
  download: false,
  selectableRows: false,
  filterType: 'dropdown',
  responsive: 'stacked',
  rowsPerPage: 10,

};

const columns = [
  {
    name: "Name",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    label: "Mobile",
    name: "Mobile",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    name: "CustomerName",
    options: {
      filter: false,
    }
  },
  {
    name: "Stage",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    name: "Deadline",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  }
];

class TRTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      enquiryStates: [],
    };
  }
  async componentDidUpdate(prevProps) {
    console.log("Old props and new props in stage coutn table", this.props.stage, prevProps.stage);
    if(this.props.stage != prevProps.stage){
      console.log("changing state ----");
      this.setState({ enquiryStates: await this.getStateData() });
    }
  }
  async getStateData() {
    console.log("Inside getStateData",this.props.stage);
    try {
        let myState = '';
      if(!this.props.stage){
            myState = "overdue";
      }else{
        myState = this.props.state;
      }//data to show before onclick of graph;
      const data = await getEnqStateData(myState);
      console.log("The result is ", data);
      if (data == null) {
        return [];
      }
      let result = data.records.map(record => {
        return [record.name, record.mobile, record.partner_name, record.stage_id[1], record.name, record.date_deadline];
      })
      return result;
    } catch (err) {
      console.log(err); // eslint-disable-line
      return [];
    }
  }
  async componentDidMount() {
    this.setState({ enquiryStates: await this.getStateData()});
  }

  getMuiTheme = () => createMuiTheme({
    overrides: {
      MUIDataTable: {
        root: {
          backgroundColor: "#FFFFCC",
        },
        paper: {
          boxShadow: "none",
        }
      },
      MUIDataTableBodyCell: {
        fixedHeader: {
          background: '#D2F7FC'
        }
      },
      MUIDataTableHeadCell: {
        root: {
          backgroundColor: "#D2F7FC"
        }
      },
      MUIDataTableSelectCell: {
        headerCell: {
          background: '#3B9AF0'
        }
      }
    }
  });

  render() {
    let enquiryStates = this.state.enquiryStates;
    console.log("Calling table render",enquiryStates);
    return (

      <MuiThemeProvider theme={this.getMuiTheme()}>
        <MUIDataTable
          title={"Leads Stage count"}
          data={enquiryStates}
          columns={columns}
          options={options}
        />
      </MuiThemeProvider>
    );
  }

}
const mapStateToProps = state => {
  console.log("enquiry stage change ", state);
  return { stage: state.enquiry_stage,team:state.team };
}
export default connect(
  mapStateToProps,
  null
)(TRTable);