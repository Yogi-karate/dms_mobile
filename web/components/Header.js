import PropTypes from 'prop-types';
import Link from 'next/link';
import Toolbar from '@material-ui/core/Toolbar';
import Grid from '@material-ui/core/Grid';
import Hidden from '@material-ui/core/Hidden';
import Avatar from '@material-ui/core/Avatar';

import MenuDrop from './MenuDrop';

import { styleToolbar } from '../lib/SharedStyles';

const optionsMenu = [
  {
    text: 'Got questions?',
    href: 'https://github.com/tramm/dms_mobile/issues',
  },
  {
    text: 'Log out',
    href: '/logout',
  },
  {
    text: 'PriceList',
    href: '/priceListForm',
  },
  {
    text: 'SaleData',
    href: '/saleData',
  }
];

function Header({ user }) {
  return (
    <div>
      <Toolbar style={styleToolbar}>
        <Grid container direction="row" justify="space-around" alignItems="center">
          <Grid item sm={10} xs={8} style={{ textAlign: 'left' }}>
            <Link prefetch href="/">
              <a href="http://dms.saboo.group">
                <img
                  src="/static/Saboo-02.png"
                  alt="Saboo logo"
                  style={{ margin: '0px auto 0px 20px' }}
                />
              </a>
            </Link>
          </Grid>
          <Grid item sm={1} xs={2}>
            {user ? (
              <div style={{ whiteSpace: ' nowrap' }}>
                {user ? (
                  <MenuDrop options={optionsMenu} src='/static/avatar.png' alt="Builder Book" />
                ) : null}
              </div>
            ) : (
                <Link prefetch href="/login">
                  <a style={{ margin: '0px 20px 0px auto' }}>Log in</a>
                </Link>
              )}
            </Grid>
            {user ? (
              <Grid item sm={1} xs={2}>
                <div>Welcome {user.name}</div>
              </Grid>
            ) : ""}
          

        </Grid>
      </Toolbar>
    </div>
  );
}

Header.propTypes = {
  user: PropTypes.shape({
    avatarUrl: PropTypes.string,
    email: PropTypes.string.isRequired,
  }),
};

Header.defaultProps = {
  user: null,
};

export default Header;
