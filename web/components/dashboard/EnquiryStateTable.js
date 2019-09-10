import React, { Component } from 'react';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import MUIDataTable from "mui-datatables";
import { getEnqStateData } from '../../lib/api/dashboard';
import { connect } from 'react-redux';

const options = {
  filter: true,
  search: true,
  print: false,
  download: false,
  selectableRows: 'none',
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
    name: "Customer Name",
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
    name: "Sales Person",
    options: {
      filter: true,
      sortDirection: 'asc'
    }
  },
  {
    name: "FollowUp Date",
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
    if (this.props.stage != prevProps.stage) {
      console.log("changing state ----");
      this.setState({ enquiryStates: await this.getStateData(this.props.stage) });
    }
  }
  async getStateData(state) {
    console.log("Inside getStateData", this.props.stage);
    try {
      const data = await getEnqStateData(state == null ? "overdue":state);
      console.log("The result is ", data);
      if (data == null) {
        return [];
      }
      let result = data.records.map(record => {
        return [record.name, record.mobile, record.partner_name, record.stage_id[1],record.user_id[1],record.activity_date_deadline];
      })
      return result;
    } catch (err) {
      console.log(err); // eslint-disable-line
      return [];
    }
  }
  async componentDidMount() {
    this.setState({ enquiryStates: await this.getStateData(this.props.stage) });
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
    let enq = "Enquiry Followup"
    let status = this.props.stage == null ? "overdue":this.props.stage;
    let header = enq+" ( "+status+" )"
    console.log("Calling table render", enquiryStates);
    return (

      <MuiThemeProvider theme={this.getMuiTheme()}>
      <MUIDataTable
        title={header}
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
  return { stage: state.lead_state, team: state.team };
}
export default connect(
  mapStateToProps,
  null
)(TRTable);