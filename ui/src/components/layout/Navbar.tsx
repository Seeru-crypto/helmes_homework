import styled from "styled-components";
import NavLink from "./NavLink";
import {links} from "../../configs";

const Navbar = () => {

    return (
        <NavbarStyle>
            {links.map((link) => (
                <NavLink key={link.href} href={link.href}>{link.text}</NavLink>
            ))}
        </NavbarStyle>
    )
}
export default Navbar

const NavbarStyle = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  background-color: transparent;
  gap: 1rem;
  padding: 1rem;

  @media (min-width: ${({theme}) => theme.breakpoints.xs}) {
    background-color: {(theme)= > theme . primaryColor
    }
  }

  @media (min-width: ${({theme}) => theme.breakpoints.sm}) {
    background-color: {(theme)= > theme . secondaryColor
    }
  }
`

const Sizes = styled(NavbarStyle)`
  @media (min-width: ${({theme}) => theme.breakpoints.xs}) {
    background-color: {(theme)= > theme . primaryColor
    }
  }

  @media (min-width: ${({theme}) => theme.breakpoints.sm}) {
    background-color: {(theme)= > theme . secondaryColor
    }
  }
`