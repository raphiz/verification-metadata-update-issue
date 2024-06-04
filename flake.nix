{
  description = "Example";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/release-24.05";
  };

  outputs = inputs @ {
    self,
    nixpkgs,
    flake-parts,
    ...
  }:
    flake-parts.lib.mkFlake {inherit inputs;} {
      systems = ["x86_64-linux" "x86_64-darwin" "aarch64-darwin" "aarch64-linux"];
      flake = {
        overlays.default = final: prev: {
          jdk = prev.jdk21_headless;
        };
      };
      perSystem = {
        config,
        system,
        ...
      }: let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [
            self.overlays.default
          ];
        };
      in {
        devShells.default = with pkgs;
          mkShellNoCC {
            buildInputs = [jdk];
          };

        formatter = pkgs.alejandra;
      };
    };
}
