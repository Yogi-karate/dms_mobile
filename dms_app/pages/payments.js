import React from 'react';
import { Grid } from '@material-ui/core';
import MUIDataTable from "mui-datatables";
import { getPaymentAccount } from '../lib/api/dashboard';
import withAuth from '../lib/withAuth';
import { connect } from 'react-redux';


class Payments extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      payments: []
    };
  }

  async getResultData() {
    try {
      console.log("The complete props ", this.props)
      const resp = await getPaymentAccount();
      if (resp == null || resp == undefined) {
        return [];
      } else {
        console.log("The data response for payment js", resp[0]);
        const paymentArray = resp.records.map((payment) => {
          console.log("Payment data", payment);
          return [payment.partner_id[1],payment.name,payment.state,payment.payment_type,payment.amount,payment.payment_date,payment.create_date];
        });
        console.log("The data payment", paymentArray);
        return ({ payments: paymentArray }); // eslint-disable-line
      }
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
  }

  async componentDidMount() {
    this.setState(await this.getResultData());
  }

  render() {
    return (
      <React.Fragment>
        {/* <PageTitle title="Tables" /> */}
        <Grid container spacing={32}>
          <Grid item xs={12}>
            <MUIDataTable
              title="Payments"
              data={this.state.payments}
              columns={["CustomerName","name","state","payment_type","amount","payment_date","create_date"]}
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
const mapStateToProps = state => {
  //const {app_state} = state;     
  console.log("state in mapping", state);
  return { user: state.user };
}
//const mapDispatchToProps = { login }
export default connect(
  mapStateToProps,
  null
)(Payments);