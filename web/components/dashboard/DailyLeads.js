import React, { Component } from 'react';

import MUIDataTable from "mui-datatables";
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import { getDailyLeads } from '../..//lib/api/admin';
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
    name: "User Name",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    label: "Enquiry Count",
    name: "Enquiry Count",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    name: "Booked",
    options: {
      filter: false,
    }
  },
];

class TRTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      leads: [],
    };
    // this.handleMobileChange = this.handleMobileChange.bind(this);
    // this.handlePinChange = this.handlePinChange.bind(this);
  }
  async componentDidUpdate(prevProps) {
    console.log("Old props and new props", this.props.team, prevProps.team);
    if(this.props.team && prevProps.team && this.props.team != prevProps.team){
      console.log("changing state ----");
      this.setState({ leads: await this.getLeads() });
    }
  }
  async getLeads() {
    console.log("Inside getting Leads");
    try {
      if(!this.props.team) return [];
      const data = await getDailyLeads(this.props.team);
      console.log("The result is ", data);
      if (data == null  || data[0] == null) {
        return [];
      }
      let result = data[0].result.map(user => {
        return [user.user_id[1], user.user_id_count, user.user_booked_id];
      })
      return result;
    } catch (err) {
      console.log(err); // eslint-disable-line
      return [];
    }
  }
  async componentDidMount() {
    this.setState({ leads: await this.getLeads()});
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
    let leads = this.state.leads;
    console.log("Calling table render",leads);
    return (

      <MuiThemeProvider theme={this.getMuiTheme()}>
        <MUIDataTable
          title={"MTD Team Performance"}
          data={leads}
          columns={columns}
          options={options}
        />
      </MuiThemeProvider>
    );
  }

}
const mapStateToProps = state => {
  //const {app_state} = state;     
  console.log("state in mapping", state);
  return { team: state.team };
}
export default connect(
  mapStateToProps,
  null
)(TRTable);