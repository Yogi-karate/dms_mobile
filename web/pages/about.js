import React from 'react';
import { Grid } from '@material-ui/core';
import MUIDataTable from "mui-datatables";
import { getPartners } from '../lib/api/admin';
import withAuth from '../lib/withAuth';
import { connect } from 'react-redux';


class Tables extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      partners: []
    };
  }

  async componentDidMount() {
    try {
      console.log("The props",this.props)
      const resp = await getPartners();
      console.log("The data from admin js", resp.partners);
      const partnerArray  = resp.partners.map((partner) => {
        console.log("Partner NAme",partner.name);
       return [partner.name,partner.mobile,partner.email,partner.tag]
      });
      console.log("The data -partner", partnerArray);
      this.setState({ partners: partnerArray }); // eslint-disable-line
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
  }

  render() {
    return (
      <React.Fragment>
        {/* <PageTitle title="Tables" /> */}
        <Grid container spacing={32}>
          <Grid item xs={12}>
            <MUIDataTable
              title = {this.props.user.email}
              data=  {this.state.partners}
              columns={["Name", "Mobile", "Email", "Tag"]}
              options={{
                filterType: 'checkbox',
              }}
            />
          </Grid>
        </Grid>
      </React.Fragment>
    )
  }
};
const mapStateToProps = state =>  {
  //const {app_state} = state;     
  console.log("state in mapping",state);
  return {user:state.user};
}
//const mapDispatchToProps = { login }
export default connect(
mapStateToProps,
null
)(Tables);