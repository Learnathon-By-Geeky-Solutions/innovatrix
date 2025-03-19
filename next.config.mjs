/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: true,
    webpack(config, { isServer }) {
      // Example of customizing webpack configuration
      if (!isServer) {
        config.resolve.fallback = { fs: false };
      }
      return config;
    },
  };
  
  export default nextConfig;
  